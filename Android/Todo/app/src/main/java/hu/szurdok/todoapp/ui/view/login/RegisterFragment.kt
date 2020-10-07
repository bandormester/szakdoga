package hu.szurdok.todoapp.ui.view.login

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
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

        loginViewModel = (activity as LoginActivity).loginViewModel

        loginViewModel.registrationStatus.observe(viewLifecycleOwner){
            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
            Log.d("vm", it.successful.toString())
            if(it.successful) (activity as LoginActivity).backToLogin()
        }

        btBack.setOnClickListener {
            (activity as LoginActivity).backToLogin()
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

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as LoginActivity).loginViewModel.clearPic()
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
        loginViewModel.setPicture(bitmap)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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