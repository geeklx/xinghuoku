package cn.udesk;

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.os.Build
import android.view.Gravity
import org.jetbrains.anko.ctx

/**
 * 创建者：
 * 时间：  2018/1/4.
 */
object UdeskPermissionUtlis {

    var dialog: AlertDialog? = null

    fun showDialog(ctx: Context, vararg permissions: String) {
        var title = ""
        var content = ""
        if (permissions.contains(Manifest.permission.CAMERA)) {
            title = "星火英语申请获取摄像头权限"
            content = "在下方弹窗中选择允许后，您可以正常扫码，并能进行主观题/头像/意见反馈的上传。您可以随时在手机设置-权限管理中取消授权。"
        } else if (permissions.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            or permissions.contains(Manifest.permission.READ_MEDIA_IMAGES)
        ) {
            title = "星火英语申请获取存储空间权限"
            content = "在下方弹窗中选择允许后，您可以正常扫码，并能进行主观题/头像/意见反馈的上传。您可以随时在手机设置-权限管理中取消授权。"
        } else if (permissions.contains(Manifest.permission.RECORD_AUDIO)) {
            title = "星火英语申请获取录音权限"
            content = "在下方弹窗中选择允许后，您可以正常录音。您可以随时在手机设置-权限管理中取消授权。"
        }
        if (title.isNotEmpty()) {
            dialog =
                AlertDialog.Builder(ctx).setCancelable(false).setTitle(title).setMessage(content)
                    .create()
            dialog?.window?.setGravity(Gravity.TOP)
            dialog?.show()
        }
    }

    fun checkPermissions(
        ctx: Activity, vararg permissions: String, onCancel: () -> Unit = {}, function: () -> Unit
    ) {
        if (UdeskMPermissionUtils.checkPermissions(ctx, *permissions)) {
            function()
        } else {
            showDialog(ctx, *permissions)
            UdeskMPermissionUtils.requestPermissionsResult(ctx,
                1,
                arrayOf(*permissions),
                object : UdeskMPermissionUtils.OnPermissionListener {
                    override fun onPermissionGranted() {
                        dialog?.dismiss()
                        function()
                    }

                    override fun onPermissionDenied() {
                        dialog?.dismiss()
                        onCancel()
                        UdeskMPermissionUtils.showTipsDialog(ctx)
                    }
                })
        }
    }

    //请求是否有权限
    fun queryPermissions(
        ctx: Activity, vararg permissions: String, noPermission: () -> Unit = {}, function: () -> Unit
    ) {
        if (UdeskMPermissionUtils.checkPermissions(ctx, *permissions)) {
            function()
        } else {
            noPermission()
        }
    }


    fun checkPermissions(
        ctx: Fragment, vararg permissions: String, onCancel: () -> Unit = {}, function: () -> Unit
    ) {
        if (UdeskMPermissionUtils.checkPermissions(ctx.ctx, *permissions)) {
            function()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                showDialog(ctx.activity, *permissions)
            }
            UdeskMPermissionUtils.requestPermissionsResult(ctx,
                1,
                arrayOf(*permissions),
                object : UdeskMPermissionUtils.OnPermissionListener {
                    override fun onPermissionGranted() {
                        dialog?.dismiss()
                        function()
                    }

                    override fun onPermissionDenied() {
                        dialog?.dismiss()
                        onCancel()
                        UdeskMPermissionUtils.showTipsDialog(ctx.ctx)
                    }
                })
        }
    }

    fun checkPermissions(
        ctx: androidx.fragment.app.Fragment,
        vararg permissions: String,
        onCancel: () -> Unit = {},
        function: () -> Unit
    ) {
        if (UdeskMPermissionUtils.checkPermissions(ctx.requireContext(), *permissions)) {
            function()
        } else {
            showDialog(ctx.requireContext(), *permissions)
            UdeskMPermissionUtils.requestPermissionsResult(ctx,
                1,
                arrayOf(*permissions),
                object : UdeskMPermissionUtils.OnPermissionListener {
                    override fun onPermissionGranted() {
                        dialog?.dismiss()
                        function()
                    }

                    override fun onPermissionDenied() {
                        dialog?.dismiss()
                        onCancel()
                        UdeskMPermissionUtils.showTipsDialog(ctx.requireContext())
                    }
                })
        }
    }

    //请求是否有权限
    fun queryPermissions(
        ctx: androidx.fragment.app.Fragment,
        vararg permissions: String,
        noPermission: () -> Unit = {},
        function: () -> Unit
    ) {
        if (UdeskMPermissionUtils.checkPermissions(ctx.requireContext(), *permissions)) {
            function()
        } else {
            noPermission()
        }
    }

    fun checkPermissions(
        ctx: Context, vararg permissions: String, onCancel: () -> Unit = {}, function: () -> Unit
    ) {
        if (UdeskMPermissionUtils.checkPermissions(ctx, *permissions)) {
            function()
        } else {
            showDialog(ctx, *permissions)
            UdeskMPermissionUtils.requestPermissionsResult(
                ctx,
                1,
                arrayOf(*permissions),
                object : UdeskMPermissionUtils.OnPermissionListener {
                    override fun onPermissionGranted() {
                        dialog?.dismiss()
                        function()
                    }

                    override fun onPermissionDenied() {
                        dialog?.dismiss()
                        onCancel()
                        UdeskMPermissionUtils.showTipsDialog(ctx)
                    }
                })
        }
    }
}