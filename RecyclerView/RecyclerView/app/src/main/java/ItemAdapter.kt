import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anslab5.Item
import com.example.anslab5.databinding.ItemViewBinding

class ItemAdapter(private val items: List<Item>, private val onItemClick: (Item) -> Unit) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>()
{
    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): ItemViewHolder
    {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int)
    {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    class ItemViewHolder(private val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(item:Item)
        {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
            binding.imageView.setImageResource(item.imageID)
        }
    }
}