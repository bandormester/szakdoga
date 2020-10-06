package hu.szurdok.todoapp.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import hu.szurdok.todoapp.MainActivity
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.register_activity.*

class RegisterFragment : Fragment() {

    private lateinit var loginViewModel : LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.register_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = (activity as MainActivity).appContainer.getLoginContainer().loginViewModel

        btBack.setOnClickListener {
            (activity as MainActivity).backToLogin()
        }

        btRegister.setOnClickListener {
            loginViewModel.register(
                etRegFullname.text.toString(),
                etRegUsername.text.toString(),
                etRegEmail.text.toString(),
                etRegPassword.text.toString())
        }

        ivRegister.setOnClickListener{
            Log.d("ivReg", "Pressed")
            askCameraPermission()
        }

    }

    private fun askCameraPermission() {
        if(ContextCompat.checkSelfPermission(requireActivity().baseContext,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf<String>(android.Manifest.permission.CAMERA),
                101)
        }
        else{
            openCamera()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val bitmap = data!!.extras!!.get("data") as Bitmap
        ivRegister.setImageBitmap(bitmap)
        loginViewModel.takePicture(bitmap)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 101){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera()
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 0)
    }
}