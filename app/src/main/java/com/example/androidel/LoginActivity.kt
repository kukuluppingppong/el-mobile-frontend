package com.example.androidel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.androidel.databinding.ActivityLoginBinding
import com.example.androidel.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.joinButton.setOnClickListener {
            val intent = Intent(applicationContext, JoinActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.btnGoogle.setOnClickListener {
            var intent = Intent(applicationContext, SendActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.btnFace.setOnClickListener {
            var intent = Intent(applicationContext, RecordActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.btnLogin.setOnClickListener {
            val idStr = binding.editId.text.toString()
            val pwStr = binding.editPw.text.toString()

            val call: Call<LoginResponse> = MyApplication.networkService.login(idStr, pwStr)

            call?.enqueue(object: Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>,
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                        Log.e("태그", response.body().toString())
                    } else {
                        Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("태그", "${t.localizedMessage}")
                }
            })
        }
    }
}