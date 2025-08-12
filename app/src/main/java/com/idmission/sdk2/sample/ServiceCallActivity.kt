package com.idmission.sdk2.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.idmission.sdk2.capture.IdMissionCaptureLauncher
import com.idmission.sdk2.capture.presentation.camera.helpers.ProcessedCapture
import com.idmission.sdk2.identityproofing.IdentityProofingSDK
import com.idmission.sdk2.sample.databinding.ActivityServiceCallBinding

class ServiceCallActivity : AppCompatActivity() {
    private lateinit var binding : ActivityServiceCallBinding
    private var processedCaptures: List<ProcessedCapture>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceCallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
    }

    private fun setListener() {

        binding.serviceId50.setOnClickListener {
            IdentityProofingSDK.idValidationAndcustomerEnroll(
                this,
                uniqueNumber = "123333"
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data ?: return
        if (requestCode != IdMissionCaptureLauncher.CAPTURE_REQUEST_CODE) return
        processedCaptures = if (!data.extras?.getParcelableArray(
                IdMissionCaptureLauncher
                    .EXTRA_PROCESSED_CAPTURES
            )
                .isNullOrEmpty()
        ) {
            data.extras?.getParcelableArray(IdMissionCaptureLauncher.EXTRA_PROCESSED_CAPTURES)
                ?.toList() as List<ProcessedCapture>?
        } else {
            emptyList()
        }
        if (processedCaptures!=null){
            PhotoResultsActivity.launch(this, processedCaptures!!)
        }
    }


}