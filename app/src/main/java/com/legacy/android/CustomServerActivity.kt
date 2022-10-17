package com.legacy.android

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.legacy.android.databinding.ActivityCustomServerBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CustomServerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomServerBinding
    private val preference by lazy { ServerPreference.getInstance(this) }
    private var server: ProxyServer = ProxyServer.default()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCustomServerBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                preference.SERVER.collect{
                    server = it

                    binding.etHost.setText(server.host)
                    binding.etPort.setText(server.port)
                    binding.etToken.setText(server.token)
                }
            }
        }

        binding.saveButton.setOnClickListener {
            if (isClear()) {
                Toast.makeText(this, "Saving server info", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    preference.saveProxyServer(
                        ProxyServer(
                            binding.etHost.text.toString(),
                            binding.etPort.text.toString(),
                            binding.etToken.text.toString()
                        )
                    )
                    finish()
                }
            }
        }
        binding.resetButton.setOnClickListener {
            Toast.makeText(this, "Proxy server set to default", Toast.LENGTH_SHORT).show()
            lifecycleScope.launch {
                delay(100)
                preference.clear()
                finish()
            }
        }
    }

    private fun isClear(): Boolean {
        var clear = true
        if (binding.etHost.text.isNullOrEmpty()) {
            binding.etHost.error = "Enter a valid Host"
            clear = false
        } else binding.etHost.error = null
        if (binding.etPort.text.isNullOrEmpty()) {
            binding.etPort.error = "Enter a valid Port"
            clear = false
        } else binding.etPort.error = null
        if (binding.etToken.text.isNullOrEmpty()) {
            binding.etToken.error = "Enter a valid token"
            clear = false
        } else binding.etToken.error = null
        return clear
    }
}