package so.codex.hawk.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import so.codex.hawk.R
import so.codex.hawk.base.BaseSingleFragmentActivity

class MainActivity : BaseSingleFragmentActivity() {
    override val containerId: Int = R.id.container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
