package com.example.quizical.feature_profile.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import com.example.quizical.databinding.FragmentSetupProfileBinding
import com.example.quizical.feature_profile.presentation.activities.EditProfileActivity
import com.example.quizical.feature_profile.presentation.activities.getProfileViewModel
import com.example.quizical.feature_profile.presentation.events.ProfileEvents
import com.example.quizical.feature_profile.presentation.states.ProfileState
import com.example.quizical.global_utils.glide_utils.loadImage
import com.example.quizical.global_utils.intent_utils.MimeTypes
import com.example.quizical.global_utils.intent_utils.openFilePicker
import com.example.quizical.global_utils.intent_utils.startActivity


class SetupProfileFragment : Fragment() {
    private val binding by lazy {
        FragmentSetupProfileBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        getProfileViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onEvent(ProfileEvents.GetCurrentUser)
        initViews()
        bindStateObservers()
    }

    private fun bindStateObservers() {
        val stateLiveData = viewModel.state.asLiveData()
        stateLiveData.observe(requireActivity()) { state ->
            state ?: return@observe
            stateGlobal(state)
        }
    }

    private fun stateGlobal(state: ProfileState) {
        state.userProfilePhoto?.let {
            binding.layoutSetupProfile.ivUser.setImageURI(it)
        }

        val currentUser = state.user

        if (currentUser.photo.isNotEmpty() && state.userProfilePhoto == null) {
            binding.layoutSetupProfile.ivUser.loadImage(currentUser.photo)
        }

        if (currentUser.name.isNotEmpty()) {
            binding.layoutSetupProfile.edtUsername.setText(currentUser.name)
        }
    }

    private fun initViews() {
        binding.layoutSetupProfile.apply {
            ivUser.setOnClickListener {
                requireActivity().openFilePicker(MimeTypes.MIME_IMAGE) { uri ->
                    uri ?: return@openFilePicker
                    viewModel.onEvent(ProfileEvents.OnImagePicked(uri))
                }
            }
            binding.btnSaveProfile.setOnClickListener {
                val name = edtUsername.text.toString()
                viewModel.onEvent(ProfileEvents.CreateUser(name, requireActivity()))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root
}

