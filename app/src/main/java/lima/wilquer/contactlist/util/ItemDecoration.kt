package lima.wilquer.contactlist.util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class ItemDecoration(val height: Int) : RecyclerView.ItemDecoration(){

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect){
            if (parent.getChildAdapterPosition(view) == 0){
                top = height
            }
            left =  height
            right = height
            bottom = height
        }
    }

}