package kr.go.seoul.seoultrail.CameraCapture;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bgg89 on 2017-11-22.
 */

public class UploadFile extends AsyncTask<String, String, String> {

    Context context; // 생성자 호출 시
    ProgressDialog mProgressDialog; // 진행 상태 다이얼로그
    String fileName; // 파일 위치

    HttpURLConnection conn = null;
    DataOutputStream dos = null;

    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 1024;
    File sourceFile;
    int serverResponseCode;
    String TAG = "FileUpload";

    public UploadFile(Context context) {
        this.context = context;
    }

    public void setPath(String uploadFilePath) {
        this.fileName = uploadFilePath;
        this.sourceFile = new File(uploadFilePath);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Image uploading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    @Override
    protected  String doInBackground(String... strings) {
        if(!sourceFile.isFile()) {
            // 해당 위치의 파일이 있는지 검사
            Log.e(TAG, "sourceFile(" + fileName + ") is Not A File");
            return null;
        } else {
            String success = "Success";
            Log.i(TAG, "sourceFile(" + fileName + ") is A File");
            try {
                FileInputStream fileInputStream = new FileInputStream(sourceFile);

                URL url = new URL(strings[0]);
                Log.i("string[0]", strings[0]);

                // Open a HTTP connection to the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/from-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);
                Log.i(TAG, "fileName: " + fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"data1\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes("newImage");
                dos.writeBytes(lineEnd);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i(TAG, "[UploadImageToServer] HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200) {

                }

                BufferedReader rd = null;
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line = null;
                while((line = rd.readLine()) != null) {
                    Log.i("Upload State", line);
                }

                fileInputStream.close();
                dos.flush();
                dos.close();
            } catch (Exception e) {
                Log.e(TAG + " Error", e.toString());
            }
            return success;
        }
    }
}
