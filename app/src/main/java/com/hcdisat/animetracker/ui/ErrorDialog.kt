package com.hcdisat.animetracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hcdisat.animetracker.databinding.FragmentErrorDialogBinding

class ErrorDialog : DialogFragment() {

    private val binding by lazy {
        FragmentErrorDialogBinding.inflate(layoutInflater)
    }

    lateinit var errorMessage: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Dialog)

        binding.errorMessage.text = errorMessage
        binding.dismiss.setOnClickListener { dismissAllowingStateLoss() }
        return binding.root
    }

    companion object {
        const val TAG = "ErrorDialog"

        fun newErrorDialog(message: String) = ErrorDialog().apply {
            errorMessage = message
        }
    }
}