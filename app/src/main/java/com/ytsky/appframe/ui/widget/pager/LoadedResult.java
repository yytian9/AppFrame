package com.ytsky.appframe.ui.widget.pager;

/**
 * author:  yytian
 * time:    2016/12/1 0001 下午 6:24
 * des:
 */

public enum LoadedResult {
    EMPTY(LoadState.EMPTY), ERROR(LoadState.ERROR), SUCCESS(LoadState.SUCCESS);
    int	state;
    public int getState() {
        return state;
    }
    private LoadedResult(int state) {
        this.state = state;
    }
}
