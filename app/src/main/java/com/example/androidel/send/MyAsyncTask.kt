package com.example.androidel.send

import android.app.ProgressDialog
import android.os.AsyncTask

class MyAsyncTask(context: SendActivity) : AsyncTask<Void?, Void?, Void?>() {
    private var progressDialog = ProgressDialog(context)
    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER) // Style - 원 모양 설정
        progressDialog.setMessage("로딩중입니다...") // Message - 표시할 텍스트
        progressDialog.setCanceledOnTouchOutside(false) // 터치시 Canceled 막기
        progressDialog.show()
    }

    override fun doInBackground(vararg params: Void?): Void? {
        for (i in 0..3) {
            try {
                Thread.sleep(2000) // 0.5초 간격 UI Update
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        progressDialog.dismiss()
    }
}