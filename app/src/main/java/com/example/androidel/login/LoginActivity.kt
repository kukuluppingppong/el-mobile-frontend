package com.example.androidel.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidel.MyApplication
import com.example.androidel.Prefs
import com.example.androidel.record.RecordActivity
import com.example.androidel.databinding.ActivityLoginBinding
import com.example.androidel.login.models.LoginResponse
import com.example.androidel.login.models.LoginResult
import com.example.androidel.login.models.TrainerResponse
import com.example.androidel.send.SendActivity
import com.example.androidel.signUp.SignUpActivity
import com.example.androidel.trainer.TrainerActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.joinButton.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
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

            val call: Call<LoginResult> = MyApplication.loginService.login(LoginResponse(idStr, pwStr))

            call?.enqueue(object: Callback<LoginResult> {
                override fun onResponse(
                    call: Call<LoginResult>,
                    response: Response<LoginResult>,
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                        MyApplication.prefs.token = response.body()!!.data.token
                        Log.e("태그", response.toString())
                        Log.e("태그", MyApplication.prefs.token!!)

                        val intent = Intent(applicationContext, RecordActivity::class.java)
                            startActivity(intent)
                            finish()
                            overridePendingTransition(0, 0)

//                        if (MyApplication.prefs.trainerId != null) {
//                            val intent = Intent(applicationContext, SendActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                            overridePendingTransition(0, 0)
//                        } else {
//                            val intent = Intent(applicationContext, TrainerActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                            overridePendingTransition(0, 0)
//                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                    Log.e("로그인 실패", "${t.localizedMessage}")
                }
            })

            val call2: Call<TrainerResponse> = MyApplication.trainerService.trainerGet()

            call2.enqueue(object: Callback<TrainerResponse> {
                override fun onResponse(
                    call: Call<TrainerResponse>,
                    response: Response<TrainerResponse>,
                ) {
                    if(response.isSuccessful) {
                        MyApplication.prefs.trainerId = response.body()!!.data[0].trainer_id.toString()
                        Log.e("사용자 트레이너 ID", MyApplication.prefs.trainerId!!)
                    }
                }

                override fun onFailure(call: Call<TrainerResponse>, t: Throwable) {
                    Log.e("트레이너 ID 실패", "${t.localizedMessage}")
                }
            })
        }
    }
}