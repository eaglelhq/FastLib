package com.aries.library.fast.widget;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.WindowManager;
import android.widget.TextView;

import com.aries.library.fast.R;
import com.aries.ui.widget.progress.UIProgressDialog;

import java.lang.ref.WeakReference;

/**
 * @Author: AriesHoo on 2018/7/23 14:38
 * @E-Mail: AriesHoo@126.com
 * Function: 快速创建网络请求loading
 * Description:
 */
public class FastLoadDialog {

    private Dialog mDialog = null;

    private Activity mActivity;
    private final WeakReference<Activity> mReference;

    public FastLoadDialog(Activity activity) {
        this(activity, new UIProgressDialog.NormalBuilder(activity)
                .setMessage(R.string.fast_loading)
                .create());
    }

    public FastLoadDialog(Activity activity, Dialog dialog) {
        this.mReference = new WeakReference<>(activity);
        this.mDialog = dialog;
    }

    /**
     * 设置是否可点击返回键关闭dialog
     *
     * @param enable
     * @return
     */
    public FastLoadDialog setCancelable(boolean enable) {
        if (mDialog != null) {
            mDialog.setCancelable(enable);
        }
        return this;
    }

    /**
     * 设置是否可点击dialog以外关闭
     *
     * @param enable
     * @return
     */
    public FastLoadDialog setCanceledOnTouchOutside(boolean enable) {
        if (mDialog != null) {
            mDialog.setCanceledOnTouchOutside(enable);
        }
        return this;
    }

    /**
     * @param msg
     * @return
     */
    public FastLoadDialog setMessage(CharSequence msg) {
        if (mDialog instanceof UIProgressDialog) {
            UIProgressDialog dialog = (UIProgressDialog) mDialog;
            TextView textView = dialog.getMessage();
            if (textView != null) {
                textView.setText(msg);
            }
        } else if (mDialog instanceof ProgressDialog) {
            ((ProgressDialog) mDialog).setMessage(msg);
        }
        return this;
    }

    /**
     * @param msg
     * @return
     */
    public FastLoadDialog setMessage(int msg) {
        mActivity = mReference.get();
        if (mActivity != null) {
            return setMessage(mActivity.getText(msg));
        }
        return this;
    }

    /**
     * @param enable 设置全透明
     * @return
     */
    public FastLoadDialog setFullTrans(boolean enable) {
        if (mDialog != null) {
            WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
            // 黑暗度
            lp.dimAmount = enable ? 0f : 0.5f;
            mDialog.getWindow().setAttributes(lp);
        }
        return this;
    }

    public Dialog getDialog() {
        return mDialog;
    }

    public void show() {
        mActivity = mReference.get();
        if (mActivity != null && mDialog != null && !mActivity.isFinishing()) {
            mDialog.show();
        }
    }

    public void dismiss() {
        mActivity = mReference.get();
        if (mDialog != null && !mActivity.isFinishing()) {
            mDialog.dismiss();
        }
    }
}
