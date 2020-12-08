package hu.szurdok.todoapp.ui.view.main.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.adapters.GroupMemberAdapter
import hu.szurdok.todoapp.data.models.User
import hu.szurdok.todoapp.ui.view.main.MainActivity
import hu.szurdok.todoapp.viewmodel.main.group.GroupDetailsViewModel
import kotlinx.android.synthetic.main.fragment_group_details.*

class GroupDetailsFragment : Fragment(), GroupMemberAdapter.MemberItemClickListener, GroupMemberAdapter.MemberItemRemoveClickListener {

    private lateinit var groupDetailsViewModel: GroupDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_group_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groupDetailsViewModel = (activity as MainActivity).mainContainer.groupDetailsViewModelFactory
            .create((activity as MainActivity).token, (activity as MainActivity).groupId, (activity as MainActivity))

        groupDetailsViewModel.group.observe(viewLifecycleOwner){
            tvGroupName.text = it.name
            if(it.hasPicture){
                groupDetailsViewModel.getGroupPicture(it.id, ivGroupDetailsPic, requireContext())
            }
        }

        groupDetailsViewModel.members.observe(viewLifecycleOwner){
            setupRecyclerView(it)
        }

        groupDetailsViewModel.removeMessage.observe(viewLifecycleOwner){
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }

        btInvite.setOnClickListener {
            groupDetailsViewModel.copyInviteCode()
            Toast.makeText(activity, "Join code copied to clipboard", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView(members : List<User>){
        lvMembers.layoutManager = LinearLayoutManager(activity)
        val groupAdapter = GroupMemberAdapter(groupDetailsViewModel, requireContext())
        groupAdapter.addAll(members)
        groupAdapter.itemClickListener = this
        lvMembers.adapter = groupAdapter
    }

    override fun onMemberSelected(member: User) {
        Toast.makeText(activity, "Clicked !", Toast.LENGTH_LONG).show()
    }

    override fun onMemberRemoved(member: User) {
        groupDetailsViewModel.removeMember(member.id)
    }
}