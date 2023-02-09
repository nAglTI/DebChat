package com.nagl.debchat.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nagl.debchat.databinding.FragmentSettingsBinding
import com.nagl.debchat.ui.BaseFragment
import com.nagl.debchat.ui.authorization.AuthorizationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SettingsViewModel> { viewModelFactoryProvider }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textNotifications.text = it
        }
        viewModel.isLogOut.observe(viewLifecycleOwner) {
            if (it) goToAuthorization()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun goToAuthorization() {
        val intent = Intent(activity, AuthorizationActivity::class.java)
        startActivity(intent)
        activity?.finishAffinity()
    }

    private fun initListeners() {
        binding.logOutButton.setOnClickListener {
            viewModel.logOut()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}