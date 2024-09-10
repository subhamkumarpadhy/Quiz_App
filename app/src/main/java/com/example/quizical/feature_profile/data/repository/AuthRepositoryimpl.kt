package com.example.quizical.feature_profile.data.repository

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.quizical.R
import com.example.quizical.feature_profile.domain.models.User
import com.example.quizical.feature_profile.domain.repository.AuthRepository
import com.example.quizical.feature_profile.presentation.models.CountryCode
import com.example.quizical.global_utils.file_utils.readRawFile
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.concurrent.TimeUnit

class AuthRepositoryimpl : AuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private var verificationId: String? = null
    private val gson = Gson()

    companion object {
        const val TIMEOUT = 60L
        private const val REF_IMAGES_FOLDER = "images"
        private const val REF_USERS_DB = "users"
        private const val TAG = "AuthRepositoryimpl"
    }

    override fun signInWithPhone(
        activity: Activity,
        phoneNumber: String,
        onSuccess: (FirebaseUser) -> Unit,
        onFailure: (Exception) -> Unit,
        onCodeSent: () -> Unit
    ) {
        if (firebaseAuth.currentUser != null) {
            onSuccess(firebaseAuth.currentUser!!)
            return
        }

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credentials: PhoneAuthCredential) {
                signInWithCredential(
                    credential = credentials,
                    onSuccess = onSuccess,
                    onFailure = onFailure,
                )
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                onFailure(exception)
            }

            override fun onCodeSent(
                verificationId: String,
                p1: PhoneAuthProvider.ForceResendingToken
            ) {
                this@AuthRepositoryimpl.verificationId = verificationId
                onCodeSent()
                Log.d(TAG, "onCodeSent: 1. code send for verification id $verificationId")
            }
        }

        val options = PhoneAuthOptions.newBuilder(firebaseAuth).apply {
            setPhoneNumber(phoneNumber)
            setTimeout(TIMEOUT, TimeUnit.SECONDS)
            setActivity(activity)
            setCallbacks(callbacks)
        }.build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun verifyOtp(
        otp: String,
        onSuccess: (FirebaseUser) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        verificationId ?: return
        val credentials = PhoneAuthProvider.getCredential(verificationId!!, otp)
        signInWithCredential(
            credential = credentials,
            onSuccess = onSuccess,
            onFailure = onFailure,
        )
    }

    private fun signInWithCredential(
        credential: PhoneAuthCredential,
        onSuccess: (FirebaseUser) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = firebaseAuth.currentUser!!
                onSuccess(user)
                Log.d(TAG, "signInWithCredential: 2. user is signed in")
            } else {
                val exceptions = task.exception!!
                onFailure(exceptions)
            }
        }
    }

    override fun createUserInDB(
        profilePhoto: Uri?,
        user: User,
        onSuccess: (User) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val shouldSkip = profilePhoto == null
        if (shouldSkip) {
            createUserInFireStore(
                name = user.name,
                profilePhotoUrl = user.photo,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
            return
        }

        uploadImage(
            profilePhoto = profilePhoto!!,
            onSuccess = { url ->
                createUserInFireStore(
                    name = user.name,
                    profilePhotoUrl = url,
                    onSuccess = onSuccess,
                    onFailure = onFailure
                )
            },
            onFailure = onFailure
        )
    }

    private fun uploadImage(
        profilePhoto: Uri,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val firebaseStorage = FirebaseStorage.getInstance().reference
        val currentUser = firebaseAuth.currentUser!!
        val uid = currentUser.uid

        val image = "profile_${System.currentTimeMillis()}.jpg"
        val imagesFullPath = "user_$uid/$image"
        firebaseStorage.child("$REF_IMAGES_FOLDER/$imagesFullPath").putFile(profilePhoto)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.metadata?.reference?.downloadUrl?.addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            val url = task2.result.toString()
                            onSuccess(url)
                        } else {
                            val exceptions = task.exception!!
                            onFailure(exceptions)
                        }
                    }
                } else {
                    val exceptions = task.exception!!
                    onFailure(exceptions)
                }
            }
    }

    private fun createUserInFireStore(
        name: String,
        profilePhotoUrl: String,
        onSuccess: (User) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val currentUser = firebaseAuth.currentUser!!
        val uid = currentUser.uid
        val ref = firestore.collection(REF_USERS_DB)

        val user = User(
            uid = uid,
            name = name,
            photo = profilePhotoUrl,
            phoneNumber = currentUser.phoneNumber ?: ""
        )

        ref.document(uid).set(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess(user)
            } else {
                val exceptions = task.exception!!
                onFailure(exceptions)
            }
        }
    }

    override fun getCurrentUser(onSuccess: (User) -> Unit, onFailure: (Exception) -> Unit) {

        val currentUser =
            if (firebaseAuth.currentUser == null) {
                onFailure(java.lang.Exception("Not Signed In"))
                return
            } else {
                firebaseAuth.currentUser!!
            }
        val uid = currentUser.uid

        getUser(
            uid = uid,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getUser(
        uid: String,
        onSuccess: (User) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val ref = firestore.collection(REF_USERS_DB)

        ref.document(uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result.toObject(User::class.java)!!
                onSuccess(user)
            } else {
                val exceptions = task.exception!!
                onFailure(exceptions)
            }
        }
    }

    override fun deleteProfile(onCompletion: (Boolean) -> Unit) {
        val currentUser =
            if (firebaseAuth.currentUser == null) {
                onCompletion(false)
                return
            } else {
                firebaseAuth.currentUser!!
            }
        val uid = currentUser.uid

        deleteUserFromStorage(
            uid = uid,
            onCompletion = { isDeletedFromStorage ->
                if (isDeletedFromStorage) {
                    deleteUserFromFireStore(
                        uid = uid,
                        onCompletion = { isDeletedFromFireStore ->
                            if (isDeletedFromFireStore) {
                                deleteUserFromAuth(onCompletion)
                            }
                        }
                    )
                }
            }
        )
    }

    private fun deleteUserFromAuth(
        onCompletion: (Boolean) -> Unit,
    ) {
        firebaseAuth.currentUser!!.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onCompletion(true)
            } else {
                onCompletion(false)
            }
        }
    }

    private fun deleteUserFromFireStore(
        uid: String,
        onCompletion: (Boolean) -> Unit,
    ) {
        val ref = firestore.collection(REF_USERS_DB)
        ref.document(uid).delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onCompletion(true)
            } else {
                onCompletion(false)
            }
        }
    }

    private fun deleteUserFromStorage(
        uid: String,
        onCompletion: (Boolean) -> Unit
    ) {
        val firebaseStorage = FirebaseStorage.getInstance().reference
        val userStorage = "user_$uid"
        firebaseStorage.child(userStorage).delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onCompletion(true)
            } else {
                onCompletion(false)
            }
        }
    }

    override fun getCountryCode(context: Context): List<CountryCode> {
        val jsonString = context.readRawFile(R.raw.ccp_english)
        val countryCodeType = object : TypeToken<List<CountryCode>>() {}.type
        return gson.fromJson(jsonString, countryCodeType)
    }
}