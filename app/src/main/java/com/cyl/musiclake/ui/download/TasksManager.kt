package com.cyl.musiclake.ui.download

import android.text.TextUtils
import android.util.SparseArray
import com.cyl.musiclake.ui.download.ui.DownloadManagerFragment
import com.cyl.musiclake.ui.download.ui.TaskItemAdapter
import com.cyl.musiclake.event.DownloadEvent
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadConnectListener
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.model.FileDownloadStatus
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.ref.WeakReference



object TasksManager {
    private var modelList = DownloadLoader.getDownloadingList()

    private val taskSparseArray = SparseArray<BaseDownloadTask>()

    private var listener: FileDownloadConnectListener? = null

    val isReady by lazy { FileDownloader.getImpl().isServiceConnected }

    fun addTaskForViewHolder(task: BaseDownloadTask) {
        taskSparseArray.put(task.id, task)
    }

    fun removeTaskForViewHolder(id: Int) {
        taskSparseArray.remove(id)
    }

    fun updateViewHolder(id: Int, holder: TaskItemAdapter.TaskItemViewHolder) {
        if (taskSparseArray.get(id) != null) {
            taskSparseArray.get(id).tag = holder
        }
    }

    fun releaseTask() {
        taskSparseArray.clear()
    }


    private fun registerServiceConnectionListener(activityWeakReference: WeakReference<DownloadManagerFragment>?) {
        if (listener != null) {
            FileDownloader.getImpl().removeServiceConnectListener(listener)
        }

        listener = object : FileDownloadConnectListener() {

            override fun connected() {
                if (activityWeakReference?.get() == null) {
                    return
                }
                activityWeakReference.get()?.postNotifyDataChanged()
            }

            override fun disconnected() {
                if (activityWeakReference?.get() == null) {
                    return
                }
                activityWeakReference.get()?.postNotifyDataChanged()
            }
        }

        FileDownloader.getImpl().addServiceConnectListener(listener)
    }

    private fun unregisterServiceConnectionListener() {
        FileDownloader.getImpl().removeServiceConnectListener(listener)
        listener = null
    }

    fun onCreate(activityWeakReference: WeakReference<DownloadManagerFragment>) {
        if (!FileDownloader.getImpl().isServiceConnected) {
            FileDownloader.getImpl().bindService()
            registerServiceConnectionListener(activityWeakReference)
        } else {
            registerServiceConnectionListener(activityWeakReference)
        }
    }

    fun onDestroy() {
        unregisterServiceConnectionListener()
        releaseTask()
    }


    operator fun get(position: Int): TasksManagerModel {
        return modelList[position]
    }


    private fun getById(id: Int): TasksManagerModel? {
        for (model in modelList) {
            if (model.tid == id) {
                return model
            }
        }
        return null
    }


    fun isDownloaded(status: Int): Boolean {
        return status == FileDownloadStatus.completed.toInt()
    }

    fun getStatus(id: Int, path: String): Int {
        return FileDownloader.getImpl().getStatus(id, path).toInt()
    }

    fun getTotal(id: Int): Long {
        return FileDownloader.getImpl().getTotal(id)
    }

    fun getSoFar(id: Int): Long {
        return FileDownloader.getImpl().getSoFar(id)
    }


    fun getModelList(): List<TasksManagerModel> {
        return modelList
    }



    fun finishTask(tid: Int) {
        doAsync {
            DownloadLoader.updateTask(tid)
            val data = DownloadLoader.getDownloadingList()
            uiThread {
                modelList= data
                EventBus.getDefault().post(DownloadEvent())
            }
        }
    }


    fun addTask(tid: Int, mid: String?, name: String?, url: String?, path: String, isCached: Boolean): TasksManagerModel? {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(mid) || TextUtils.isEmpty(path)) {
            return null
        }

        val model = getById(tid)
        if (model != null) {
            return model
        }
        val newModel = DownloadLoader.addTask(tid, mid, name, url, path, isCached)
        if (newModel != null) {
            modelList.add(newModel)
        }
        return newModel
    }

}
