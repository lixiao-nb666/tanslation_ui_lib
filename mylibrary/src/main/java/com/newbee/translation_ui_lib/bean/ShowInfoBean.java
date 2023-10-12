package com.newbee.translation_ui_lib.bean;

public class ShowInfoBean{
        String title;
        String content;
        boolean hideContentTitle;

        public ShowInfoBean() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isHideContentTitle() {
            return hideContentTitle;
        }

        public void setHideContentTitle(boolean hideContentTitle) {
            this.hideContentTitle = hideContentTitle;
        }

        @Override
        public String toString() {
            return "ShowInfoBean{" +
                    "title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", hideContentTitle=" + hideContentTitle +
                    '}';
        }
    }
