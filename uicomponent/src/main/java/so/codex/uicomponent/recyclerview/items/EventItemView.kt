package so.codex.uicomponent.recyclerview.items

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import kotlinx.android.synthetic.main.view_event_item.view.*
import so.codex.uicomponent.R
import so.codex.uicomponent.textViewDelegate

/**
 * Composition of view for showing elements of Event in list
 * @author YorkIsMine
 */
class EventItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : FrameLayout(context, attrs, defStyleAttr){
    /**
     * Inflate view and attach
     */
    val view: View = View.inflate(context, R.layout.view_event_item, this)

    /**
     * user image
     */
    private val userImage: ImageView = view.user_image

    /**
     * title of event
     */
    var title by textViewDelegate(view.event_title)

    /**
     * time of event's error
     */
    var time by textViewDelegate(view.time_of_error)

    /**
     * count of the same errors
     */
    var count by textViewDelegate(view.tv_count_event){v, text ->
        if(text.isEmpty() || text == "0") v.visibility = View.GONE
        else v.visibility = View.VISIBLE
    }

    fun setUserImageResource(resource: Int){
        userImage.setImageResource(resource)
    }


}