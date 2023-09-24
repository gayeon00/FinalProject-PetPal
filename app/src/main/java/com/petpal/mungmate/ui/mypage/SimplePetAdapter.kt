package com.petpal.mungmate.ui.mypage

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.petpal.mungmate.databinding.RowPetSimpleBinding

class SimplePetAdapter :
    ListAdapter<SimplePetUiState, SimplePetAdapter.SimplePetViewHolder>(SimplePetUiStateDiffCallback()) {
    inner class SimplePetViewHolder(private val rowPetSimpleBinding: RowPetSimpleBinding) :
        ViewHolder(rowPetSimpleBinding.root) {

        fun bind(simplePetUiState: SimplePetUiState) {
            rowPetSimpleBinding.textView.text = simplePetUiState.name
            // 채팅방 사진
            val userImageRef = Firebase.storage.reference.child(simplePetUiState.imageUrl)
            userImageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(itemView)
                    .load(uri)
                    .into(rowPetSimpleBinding.imageRowSimplePet)
            }.addOnFailureListener {
                Log.e("SimplePetAdapter", "pet 사진 로드 실패")
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SimplePetAdapter.SimplePetViewHolder {
        val rowBinding = RowPetSimpleBinding.inflate(LayoutInflater.from(parent.context))


        return SimplePetViewHolder(rowBinding)
    }

    override fun onBindViewHolder(holder: SimplePetAdapter.SimplePetViewHolder, position: Int) {
        holder.bind(getItem(position) as SimplePetUiState)
    }
}