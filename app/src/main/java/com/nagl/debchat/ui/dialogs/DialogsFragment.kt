package com.nagl.debchat.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nagl.debchat.R
import com.nagl.debchat.data.model.Dialog
import com.nagl.debchat.databinding.FragmentDialogsBinding
import com.nagl.debchat.ui.BaseFragment
import com.nagl.debchat.ui.dialogs.chat.ChatFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogsFragment : BaseFragment(), DialogAdapter.OnItemClickListener {

    private var _binding: FragmentDialogsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DialogsViewModel> { viewModelFactoryProvider }

    private val dialogAdapter by lazy { DialogAdapter(this) }
    private val dialogsList: ArrayList<Dialog> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogsBinding.inflate(inflater)
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dialogsListRecyclerView.adapter = dialogAdapter
        viewModel.getUserToken()
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        with(viewModel) {
            isLoading.observe(viewLifecycleOwner) { state ->
                when (state) {
                    true -> showLoading()
                    false -> hideLoading()
                }
            }

            userToken.observe(viewLifecycleOwner) {
                if (it != null && it.token.isNotEmpty()) {
                    binding.userToken = it
                    getUserDialogs(it.token)
                }
            }

            dialogs.observe(viewLifecycleOwner) {
                if (!it.isNullOrEmpty())
                    initDialogsView(it)
                else
                    showShortSnackBar("Нет доступных чатов")
            }
        }
    }

    private fun initDialogsView(dialogs: List<Dialog>) {
        dialogsList.clear()
        dialogsList.addAll(dialogs)
        dialogAdapter.submitList(dialogsList)
    }

    private fun initListeners() {
        binding.scheduleFragmentSwipeRefresh.setOnRefreshListener {
            showLoading()
            if (binding.userToken?.token?.isEmpty() == true) {
                viewModel.getUserToken()
            } else {
                viewModel.getUserDialogs(binding.userToken!!.token)
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            //scheduleViewPager.visibility = View.GONE
        }
    }

    private fun hideLoading() {
        binding.apply {
            progressBar.visibility = View.GONE
            scheduleFragmentSwipeRefresh.isRefreshing = false
        }
    }

    override fun onDialogClick(dialog: Dialog) {
        val bundle = bundleOf(
            ChatFragment.CHAT_ARGS to dialog
        )
        findNavController().navigate(R.id.action_navigation_dialogs_to_chatFragment, bundle)
    }
}