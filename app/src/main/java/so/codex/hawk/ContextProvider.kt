package so.codex.hawk

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri

/**
 * Class for providing context to DefaultImageLoader
 * @see DefaultImageLoader
 */
class ContextProvider : ContentProvider() {

    companion object {
        /**
         * Providable context
         */
        lateinit var mContext: Context
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    /**
     * Init needed context
     */
    override fun onCreate(): Boolean {
        mContext = context!!

        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0;
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0;
    }

    override fun getType(uri: Uri): String? {
        return null
    }

}