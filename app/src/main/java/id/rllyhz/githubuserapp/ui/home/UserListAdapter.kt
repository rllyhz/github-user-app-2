package id.rllyhz.githubuserapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.databinding.ItemUserBinding

class UserListAdapter : ListAdapter<User, UserListAdapter.UserListViewHolder>(UserComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    // viewholder
    inner class UserListViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatarUrl)
//                    .apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_background))
//                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(sivItemUserAvatar)

                tvItemUserUsername.text = user.username

                callback?.onDetailIconClick(user)
            }
        }
    }

    // diffutil for handling comparison of items
    class UserComparator() : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem // cause already using data class
    }

    // for item click listener
    interface ItemClickCallback {
        fun onDetailIconClick(user: User)
    }

    private var callback: ItemClickCallback? = null

    fun setOnItemListener(listener: ItemClickCallback) {
        callback = listener
    }
}