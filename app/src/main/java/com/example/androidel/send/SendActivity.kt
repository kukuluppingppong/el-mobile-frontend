package com.example.androidel.send

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidel.MyApplication
import com.example.androidel.R
import com.example.androidel.databinding.ActivitySendBinding
import java.time.LocalDate

class SendActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySendBinding.inflate(layoutInflater) }
    private lateinit var sendAdapter: SendAdapter

    private var videoArray = Array<Uri?>(3) { null }
    private var imageArray = Array<Uri?>(3) { null }

    val OPEN_GALLERY = 1002
    private val VIDEO_FILE_REQUEST = 1003
    var btnSelect = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val dayList = arrayListOf(binding.day1, binding.day2, binding.day3)
        val timeList = arrayListOf(binding.time1, binding.time2, binding.time3)
        val intakeList = arrayListOf(binding.intake1, binding.intake2, binding.intake3)
        val ratingList = arrayListOf(binding.ratingBar1, binding.ratingBar2, binding.ratingBar3)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var date = LocalDate.now()
            binding.today.text = String.format("%d년 %d월 %d일", date.year, date.monthValue, date.dayOfMonth)
        }

        binding.btnExercise.isSelected = true

        binding.btnExercise.setOnClickListener {
            binding.btnExercise.isSelected = true
            binding.btnFood.isSelected = false
            binding.scrollView2.visibility = View.INVISIBLE
            binding.exerciseLayout.visibility = View.VISIBLE
        }

        binding.btnFood.setOnClickListener {
            binding.btnExercise.isSelected = false
            binding.btnFood.isSelected = true
            binding.scrollView2.visibility = View.VISIBLE
            binding.exerciseLayout.visibility = View.INVISIBLE
        }

        sendAdapter = SendAdapter(
            onClick = {
                initImageViewProfile { navigateVideo() }
            }
        )

        binding.recyclerView.adapter = sendAdapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.btnFood1.setOnClickListener {
            btnSelect = 1
            initImageViewProfile { navigateGallery() }
        }

        binding.btnFood2.setOnClickListener {
            btnSelect = 2
            initImageViewProfile { navigateGallery() }
        }

        binding.btnFood3.setOnClickListener {
            btnSelect = 3
            initImageViewProfile { navigateGallery() }
        }

        binding.btnNext.setOnClickListener {
            for (i in 0..2) {
                if (videoArray[i] != null) {
                    SendOkhttp.sendVideo(videoArray[i]!!, applicationContext, MyApplication.prefs.trainerId!!.toInt(), i+1,
                        sendAdapter.parts[i].joinToString(", ", "[", "]"),
                        sendAdapter.sets[i].toString(), sendAdapter.reps[i].toString(), sendAdapter.degree[i].toString()
                    )
                }
            }

            for (i in 0..2) {
                if (imageArray[i] != null) {
                    SendOkhttp.send(imageArray[i]!!, applicationContext, MyApplication.prefs.trainerId!!.toInt(),
                        dayList[i].text.toString(),
                        timeList[i].text.substring(0, 2) + timeList[i].text.substring(3, 5),
                        intakeList[i].text.toString(),
                        ratingList[i].rating.toInt())
                }
            }
        }

        var record = arrayListOf(binding.btnRecord1, binding.btnRecord2, binding.btnRecord3)

        for (i in record.indices) {
            record[i].setOnClickListener {
                lateinit var btnIntake : ArrayList<Button>
                var choiceIntake = ArrayList<String>()
                var choiceMaxIntake = 1

                var dialogView = View.inflate(this, R.layout.send_food_dialog, null)
                var dlg = AlertDialog.Builder(this).create()
                dlg.setView(dialogView)
                dlg.show()

                var hour = dialogView.findViewById<EditText>(R.id.hour)
                var minute = dialogView.findViewById<EditText>(R.id.minute)

                var lightly = dialogView.findViewById<Button>(R.id.lightly)
                var moderately = dialogView.findViewById<Button>(R.id.moderately)
                var full = dialogView.findViewById<Button>(R.id.full)

                btnIntake = arrayListOf(lightly, moderately, full)

                for (i in btnIntake.indices) {
                    btnIntake[i].setOnClickListener {
                        if (choiceIntake.size < choiceMaxIntake) {
                            if (!btnIntake[i].isSelected) {
                                btnIntake[i].isSelected = true
                                choiceIntake.add(btnIntake[i].text.toString())
                            } else {
                                btnIntake[i].isSelected = false
                                choiceIntake.removeAt(choiceIntake.indexOf(btnIntake[i].text.toString()))
                            }
                        } else {
                            if (btnIntake[i].isSelected) {
                                btnIntake[i].isSelected = false
                                choiceIntake.removeAt(choiceIntake.indexOf(btnIntake[i].text.toString()))
                            }
                        }
                    }
                }

                var dialogRatingBar = dialogView.findViewById<RatingBar>(R.id.ratingBar)

                var btnSave = dialogView.findViewById<Button>(R.id.btnSave)

                var time = arrayListOf(binding.time1, binding.time2, binding.time3)
                var intake = arrayListOf(binding.intake1, binding.intake2, binding.intake3)
                var ratingBar = arrayListOf(binding.ratingBar1, binding.ratingBar2, binding.ratingBar3)

                btnSave.setOnClickListener {
                    time[i].text = "${hour.text}:${minute.text}"
                    intake[i].text = choiceIntake.joinToString()
                    ratingBar[i].rating = dialogRatingBar.rating

                    dlg.dismiss()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == OPEN_GALLERY)
        {
            if(resultCode == RESULT_OK)
            {
                var currentImageUri = data?.data!!
                try{
                    currentImageUri?.let {
                        val source = ImageDecoder.createSource(this.contentResolver, currentImageUri)
                        val bitmap = ImageDecoder.decodeBitmap(source)

                        when (btnSelect) {
                            1 -> {
                                binding.btnFood1?.setImageBitmap(Bitmap.createScaledBitmap(bitmap, binding.btnFood1.width-2, binding.btnFood1.height-2, false))
                                binding.text1.visibility = View.GONE
                                imageArray[0] = currentImageUri
                            }
                            2 -> {
                                binding.btnFood2?.setImageBitmap(Bitmap.createScaledBitmap(bitmap, binding.btnFood1.width-2, binding.btnFood1.height-2, false))
                                binding.text2.visibility = View.GONE
                                imageArray[1] = currentImageUri
                            }
                            3 -> {
                                binding.btnFood3?.setImageBitmap(Bitmap.createScaledBitmap(bitmap, binding.btnFood1.width-2, binding.btnFood1.height-2, false))
                                binding.text3.visibility = View.GONE
                                imageArray[2] = currentImageUri
                            }
                        }
                    }
                }catch(e: Exception) {
                    e.printStackTrace()
                }
                if(videoArray.size == 3 && imageArray.isNotEmpty()) {
                    binding.btnNext.visibility = View.VISIBLE
                }
                Log.e("이미지 URI 배열 확인", imageArray.contentToString())
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }

        if(requestCode == VIDEO_FILE_REQUEST) {
            if(resultCode == RESULT_OK)
            {
                val videoUri = data?.clipData
                if (videoUri == null) {
                    sendAdapter.setVideoOne(data?.data)
                    videoArray[sendAdapter.select] = data?.data
                }

                var count = data?.clipData?.itemCount
                if(count == null) {
                    count = 1
                }
                try {
                    videoUri?.let {
                        for (i in 0 until count) {
                            val imageUri = videoUri.getItemAt(i).uri
                            videoArray[i] = imageUri
                        }
                        sendAdapter.setVideoList(videoArray)
                    }
                } catch(e: Exception)
                {
                    e.printStackTrace()
                }
                Log.e("비디오 URI 배열 확인", videoArray.contentToString())
            }

            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "영상 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            OPEN_GALLERY -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "권한을 승인하셨습니다.", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initImageViewProfile(uploadAction: () -> Unit) {
        when {
            // 갤러리 접근 권한이 있는 경우
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                uploadAction()
            }

            // 갤러리 접근 권한이 없는 경우 & 교육용 팝업을 보여줘야 하는 경우
            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                showPermissionContextPopup()
            }

            // 권한 요청 하기(requestPermissions) -> 갤러리 접근(onRequestPermissionResult)
            else ->
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    OPEN_GALLERY
                )
        }
    }

    fun navigateVideo() {
        val vod_intent = Intent()
        //ACTION_GET_CONTENT을 하게되면, 갤러리뿐 아니라 다른 저장공간에도 접근한다.
//                      vod_intent.setAction(Intent.ACTION_GET_CONTENT);
        vod_intent.type = "video/*"
        vod_intent.action = Intent.ACTION_GET_CONTENT
        vod_intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(vod_intent, VIDEO_FILE_REQUEST)
    }

    fun navigateGallery() {
        val intent = Intent()
        // 가져올 컨텐츠들 중에서 Image 만을 가져온다.
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        // 갤러리에서 이미지를 선택한 후, 프로필 이미지뷰를 수정하기 위해 갤러리에서 수행한 값을 받아오는 startActivityForeResult를 사용한다.
        startActivityForResult(intent, OPEN_GALLERY)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("정보를 등록하기 위해서는 갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), OPEN_GALLERY)
            }
            .setNegativeButton("취소하기") { _, _ ->
            }
            .create()
            .show()
    }
}