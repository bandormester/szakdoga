package hu.szurdok.todoapp.ui.view.main.group

import android.app.Activity
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
import androidx.core.view.drawToBitmap
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.observe
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.viewmodel.main.group.ChooseGroupViewModel
import kotlinx.android.synthetic.main.create_group_fragment.*
import kotlinx.android.synthetic.main.create_group_fragment.ivCreateGroup


class CreateGroupFragment(
    private val chooseGroupViewModel: ChooseGroupViewModel
) : DialogFragment(){

    companion object{
        const val TAG = "CreateGroupDialogTag"
    }

    private var hasPicture = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.create_group_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btBackToChoose.setOnClickListener {
            dismiss()
        }

        ivCreateGroup.setOnClickListener {
            if(hasPicture){
                ivCreateGroup.setImageResource(R.drawable.ic_launcher_background)
            }
            else {
                Log.d("ivRegGroup", "Pressed")
                askCameraPermission()
            }
            hasPicture = !hasPicture
        }

        btFinishGroup.setOnClickListener {
            chooseGroupViewModel.createStatus.observe(viewLifecycleOwner){
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                dismiss()
            }

            chooseGroupViewModel.createGroup(ivCreateGroup.drawToBitmap(),etGroupName.text.toString(), etGroupDescription.text.toString(), etJoinCode.text.toString(), hasPicture)
        }
    }

    override fun onResume() {
        super.onResume()
        val width = (resources.displayMetrics.widthPixels * 0.8).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.8).toInt()
        dialog?.window?.setLayout(width, height)
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

        if(requestCode == 101 && resultCode == Activity.RESULT_OK) {
            val bitmap = data!!.extras!!.get("data") as Bitmap
            ivCreateGroup.setImageBitmap(bitmap)
        }
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