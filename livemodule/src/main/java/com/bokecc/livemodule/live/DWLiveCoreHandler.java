package com.bokecc.livemodule.live;

import android.util.Log;

import com.bokecc.livemodule.BuildConfig;
import com.bokecc.livemodule.live.chat.module.ChatEntity;
import com.bokecc.livemodule.utils.ThreadUtils;
import com.bokecc.sdk.mobile.live.DWLive;
import com.bokecc.sdk.mobile.live.DWLiveEngine;
import com.bokecc.sdk.mobile.live.DWLiveListener;
import com.bokecc.sdk.mobile.live.DWLivePlayer;
import com.bokecc.sdk.mobile.live.Exception.DWLiveException;
import com.bokecc.sdk.mobile.live.Exception.ErrorCode;
import com.bokecc.sdk.mobile.live.listener.LiveChangeSourceListener;
import com.bokecc.sdk.mobile.live.pojo.Answer;
import com.bokecc.sdk.mobile.live.pojo.BanChatBroadcast;
import com.bokecc.sdk.mobile.live.pojo.BroadCastAction;
import com.bokecc.sdk.mobile.live.pojo.BroadCastMsg;
import com.bokecc.sdk.mobile.live.pojo.ChatMessage;
import com.bokecc.sdk.mobile.live.pojo.LiveInfo;
import com.bokecc.sdk.mobile.live.pojo.LiveLineInfo;
import com.bokecc.sdk.mobile.live.pojo.LiveQualityInfo;
import com.bokecc.sdk.mobile.live.pojo.LotteryAction;
import com.bokecc.sdk.mobile.live.pojo.PracticeInfo;
import com.bokecc.sdk.mobile.live.pojo.PracticeRankInfo;
import com.bokecc.sdk.mobile.live.pojo.PracticeStatisInfo;
import com.bokecc.sdk.mobile.live.pojo.PracticeSubmitResultInfo;
import com.bokecc.sdk.mobile.live.pojo.PrivateChatInfo;
import com.bokecc.sdk.mobile.live.pojo.Question;
import com.bokecc.sdk.mobile.live.pojo.QuestionnaireInfo;
import com.bokecc.sdk.mobile.live.pojo.QuestionnaireStatisInfo;
import com.bokecc.sdk.mobile.live.pojo.RoomInfo;
import com.bokecc.sdk.mobile.live.pojo.SettingInfo;
import com.bokecc.sdk.mobile.live.pojo.TeacherInfo;
import com.bokecc.sdk.mobile.live.pojo.UserRedminAction;
import com.bokecc.sdk.mobile.live.pojo.Viewer;
import com.bokecc.sdk.mobile.live.rtc.CCRTCRender;
import com.bokecc.sdk.mobile.live.rtc.RtcClient;
import com.bokecc.sdk.mobile.live.widget.DocView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ????????????????????????????????????
 */
public class DWLiveCoreHandler {

    private static final String TAG = DWLiveCoreHandler.class.getSimpleName();
    private static final DWLiveCoreHandler dwLiveCoreHandler = new DWLiveCoreHandler();


    /**
     * ??????DWLiveCoreHandler???????????????
     *
     * @return dwLiveCoreHandler
     */
    public static DWLiveCoreHandler getInstance() {
        return dwLiveCoreHandler;
    }

    /**
     * ??????????????????
     */
    private DWLiveCoreHandler() {

    }

    /******************************* ?????????????????????????????? ***************************************/

    private DWLiveQAListener dwLiveQAListener;

    /**
     * ??????????????????
     */
    public void setDwLiveQAListener(DWLiveQAListener listener) {
        dwLiveQAListener = listener;
    }

    private DWLiveChatListener dwLiveChatListener;

    /**
     * ??????????????????
     */
    public void setDwLiveChatListener(DWLiveChatListener listener) {
        dwLiveChatListener = listener;
    }

    private DWLiveRoomListener dwLiveRoomListener;

    /**
     * ???????????????????????????
     */
    public void setDwLiveRoomListener(DWLiveRoomListener dwLiveRoomListener) {
        this.dwLiveRoomListener = dwLiveRoomListener;
    }

    private DWLiveVideoListener dwLiveVideoListener;

    /**
     * ????????????????????????
     */
    public void setDwLiveVideoListener(DWLiveVideoListener dwLiveVideoListener) {
        this.dwLiveVideoListener = dwLiveVideoListener;
    }

    private DWLiveFunctionListener dwLiveFunctionListener;

    /**
     * ?????????????????????????????????????????????/???????????????????????????
     */
    public void setDwLiveFunctionListener(DWLiveFunctionListener dwLiveFunctionListener) {
        this.dwLiveFunctionListener = dwLiveFunctionListener;
    }

    private DWLiveMoreFunctionListener dwLiveMoreFunctionListener;

    /**
     * ????????????????????????????????????????????????????????????
     */
    public void setDwLiveMoreFunctionListener(DWLiveMoreFunctionListener dwLiveMoreFunctionListener) {
        this.dwLiveMoreFunctionListener = dwLiveMoreFunctionListener;
    }

    private DWLiveRTCListener dwLiveRTCListener;

    /**
     * ???????????????????????? -- ?????????????????????????????????
     */
    public void setDwLiveRTCListener(DWLiveRTCListener dwLiveRTCListener) {
        this.dwLiveRTCListener = dwLiveRTCListener;
    }

    private DWLiveRTCStatusListener dwLiveRTCStatusListener;

    /**
     * ?????????????????????????????? -- ??????????????????????????????
     */
    public void setDwLiveRTCStatusListener(DWLiveRTCStatusListener dwLiveRTCStatusListener) {
        this.dwLiveRTCStatusListener = dwLiveRTCStatusListener;
    }

    private UserListener userListener;

    /**
     * ??????????????????????????????????????????
     *
     * @param userListener
     */
    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
    }

    /******************************* ??????"??????"??????/???????????? ***************************************/


    private DWLivePlayer dwLivePlayer;

    private DocView docView;

    /**
     * ???????????????
     *
     * @param player ?????????
     */
    public void setPlayer(DWLivePlayer player) {
        this.dwLivePlayer = player;
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null) {
            dwLive.setDWLivePlayer(this.dwLivePlayer);
        }
    }

    /**
     * ????????????????????????
     *
     * @param docView ??????????????????
     */
    public void setDocView(DocView docView) {
        this.docView = docView;
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null) {
            dwLive.setDWLivePlayDocView(this.docView);
        }
    }

    public DWLivePlayer getPlayer() {
        return dwLivePlayer;
    }

    /******************************* ??????????????????????????? ***************************************/

    private final static String ONLY_VIDEO_TEMPLATE_TYPE = "1";

    /**
     * ????????????????????????'??????'
     */
    public boolean hasPdfView() {
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null && dwLive.getTemplateInfo() != null) {
            return dwLive.getTemplateInfo().hasDoc();
        }
        return false;
    }

    /**
     * ????????????????????????'??????'
     */
    public boolean hasChatView() {
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null && dwLive.getTemplateInfo() != null) {
            return dwLive.getTemplateInfo().hasChat();
        }
        return false;
    }

    /**
     * ????????????????????????'??????'
     */
    public boolean hasQaView() {
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null && dwLive.getTemplateInfo() != null) {
            return dwLive.getTemplateInfo().hasQa();
        }
        return false;
    }

    /**
     * ??????????????????????????????(????????????-->??????)
     * <p>
     * ??????<????????????-->??????>???TemplateInfo.type == 1
     *
     * @return true ?????????false??????
     */
    public boolean isOnlyVideoTemplate() {
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null && dwLive.getTemplateInfo() != null) {
            return ONLY_VIDEO_TEMPLATE_TYPE.equals(dwLive.getTemplateInfo().getType());
        }
        return false;
    }

    /**
     * ????????????????????????
     *
     * @return true ?????? false ?????????
     */
    public boolean isShowUserCount() {
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null && dwLive.getRoomInfo() != null) {
            return dwLive.getRoomInfo().getShowUserCount() == 1;
        }
        return false;
    }

    /**
     * ?????????????????????
     *
     * @return true ?????? false ?????????
     */
    public boolean isShowQuality() {
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null && dwLive.getRoomInfo() != null) {
            return (dwLive.getRoomInfo().getMultiQuality() == 1);
        }
        return false;
    }

    /**
     * ?????????????????????
     *
     * @return true ?????? false ?????????
     */
    public boolean isOpenMarquee() {
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null && dwLive.getRoomInfo() != null) {
            return dwLive.getRoomInfo().getOpenMarquee() == 1;
        }
        return false;
    }

    /**
     * ??????????????????
     *
     * @return true ?????? false ?????????
     */
    public boolean isOpenBarrage() {
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null && dwLive.getRoomInfo() != null) {
            return dwLive.getRoomInfo().getBarrage() == 1;
        }
        return false;
    }


    //******************************* ??????SDK?????????????????????????????? ***************************************/

    /**
     * ????????????
     */
    public void start() {
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null) {
            // ?????????????????? ????????? ??? ????????????
            dwLive.setDWLivePlayParams(dwLiveListener, DWLiveEngine.getInstance().getContext());
            // ????????????????????????
            dwLive.start();
            // ???????????????????????????
            DWLive.getInstance().getPracticeStatis("");
        }
    }

    /**
     * ????????????
     */
    public void stop() {
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null) {
            dwLive.stop();
        }
    }

    /**
     * ????????????
     */
    public void destroy() {
        dwLiveQAListener = null;
        dwLiveChatListener = null;
        dwLiveVideoListener = null;
        dwLiveFunctionListener = null;
        dwLiveMoreFunctionListener = null;
        dwLiveRTCListener = null;
        dwLiveRTCStatusListener = null;
        dwLiveRoomListener = null;
        userListener = null;
        isRtcing = false;
        if (docView != null) {
            docView = null;
        }
        if (dwLivePlayer != null) {
            dwLivePlayer = null;
        }
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null) {
            dwLive.onDestroy();
        }
    }

    /**
     * ???????????????
     *
     * @param quality quality
     */
    public void changeQuality(int quality, LiveChangeSourceListener changeLineCallback) {
        DWLive.getInstance().changeQuality(quality, changeLineCallback);
    }

    /**
     * ????????????
     *
     * @param lineIndex ????????????
     */
    public void changeLine(int lineIndex, LiveChangeSourceListener changeLineCallback) {
        DWLive.getInstance().changeLine(lineIndex, changeLineCallback);
    }

    /**
     * ??????????????????
     *
     * @param playMode DWLive.PlayMode
     */
    public void changePlayMode(DWLive.LivePlayMode playMode, LiveChangeSourceListener changeCallBack) {
        DWLive.getInstance().changePlayMode(playMode, changeCallBack);
    }

    // ????????????????????????UI
    public void updatePlayModeUI(DWLive.LivePlayMode playMode) {
        if (dwLiveVideoListener != null) {
            dwLiveVideoListener.onChangePlayMode(playMode);
        }

    }

    //----------------------------------- ??????SDK(DWLive)?????????????????????????????? -----------------------/


    /**
     * ??????????????????
     *
     * @return ???????????? ?????????LiveInfo.liveStartTime ????????????????????????LiveInfo.liveDuration "??????????????????????????????s???????????????????????????-1"
     */
    public LiveInfo getLiveInfo() {
        return DWLive.getInstance().getLiveInfo();
    }

    public Viewer getViewer() {
        return DWLive.getInstance().getViewer();
    }

    public RoomInfo getRoomInfo() {
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null) {
            return dwLive.getRoomInfo();
        }
        return null;
    }

    /**
     * ??????????????????
     */
    public void sendRollCall() {
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null) {
            dwLive.sendRollCall();
        }
    }

    private Map<String, ArrayList<Integer>> practiceResultIndexs;  // ???????????????????????????

    /**
     * ???????????????????????????
     *
     * @param practiceId    ?????????ID
     * @param answerOptions ??????????????????????????????
     */
    public void sendPracticeAnswer(String practiceId, ArrayList<String> answerOptions) {
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null) {
            dwLive.sendPracticeAnswer(practiceId, answerOptions);
        }
    }

    // ???????????????????????????
    public void cachePracticeResult(String practiceId, ArrayList<Integer> resultIndexs) {
        if (practiceResultIndexs == null) {
            practiceResultIndexs = new HashMap<>();
        }
        practiceResultIndexs.put(practiceId, resultIndexs);
    }

    /**
     * ?????????????????????????????????????????????ID???
     *
     * @param practiceId ?????????ID
     */
    public ArrayList<Integer> getPracticeResult(String practiceId) {
        if (practiceResultIndexs == null) {
            return null;
        }
        return practiceResultIndexs.get(practiceId);
    }


    //--------------------------------- ?????????UI??????????????????(????????????) -----------------------------------/

    /**
     * ????????????????????????
     *
     * @param chatEntity ????????????????????????????????????
     */
    public void jump2PrivateChat(ChatEntity chatEntity) {
        if (dwLiveMoreFunctionListener != null) {
            dwLiveMoreFunctionListener.jump2PrivateChat(chatEntity);
        }
    }

    /**
     * ????????????????????????
     *
     * @param msg ??????????????????
     */
    public void showError(String msg) {
        if (dwLiveRoomListener != null) {
            dwLiveRoomListener.showError(msg);
        }

    }

    /******************************* ???????????????????????????????????? ***************************************/

    private final DWLiveListener dwLiveListener = new DWLiveListener() {

        /**
         * ????????????????????????
         * @param info SettingInfo
         */
        @Override
        public void onRoomSettingInfo(SettingInfo info) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onRoomSettingInfo:" + Thread.currentThread().getName() + "," + info.toString());
                ThreadUtils.checkIsOnMainThread();
            }

        }

        /**
         * ????????????????????????????????????
         *
         * @param isVideoMain ????????????????????????
         */
        @Override
        public void onSwitchVideoDoc(boolean isVideoMain) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onSwitchVideoDoc:" + Thread.currentThread().getName() + ",isVideoMain = " + isVideoMain);
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveRoomListener != null) {
                dwLiveRoomListener.onSwitchVideoDoc(isVideoMain);
            }
        }


        /**
         * ??????????????????
         * @param infoList List<TeacherInfo>
         */
        @Override
        public void onOnlineTeachers(List<TeacherInfo> infoList) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onOnlineTeachers:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
        }

        @Override
        public void onHistoryQuestionAnswer(List<Question> questions, List<Answer> answers) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onHistoryQuestionAnswer:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveQAListener != null) {
                dwLiveQAListener.onHistoryQuestionAnswer(questions, answers);
            }
        }

        /**
         * ??????
         *
         * @param question ????????????
         */
        @Override
        public void onQuestion(Question question) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onQuestion:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveQAListener != null) {
                dwLiveQAListener.onQuestion(question);
            }
        }

        /**
         * ???????????????????????????????????????
         *
         * @param questionId ??????id
         */
        @Override
        public void onPublishQuestion(String questionId) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPublishQuestion:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveQAListener != null) {
                dwLiveQAListener.onPublishQuestion(questionId);
            }
        }

        /**
         * ??????
         *
         * @param answer ????????????
         */
        @Override
        public void onAnswer(Answer answer) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onAnswer:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveQAListener != null) {
                dwLiveQAListener.onAnswer(answer);
            }
        }

        /**
         * ?????????????????????
         *
         * @param status ??????PLAYING, PREPARING???2?????????
         */
        @Override
        public void onLiveStatus(DWLive.PlayStatus status) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onLiveStatus:" + Thread.currentThread().getName() + "," + status.toString());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveVideoListener != null) {
                dwLiveVideoListener.onLiveStatus(status);
            }
        }

        /**
         * ?????????
         *
         * @param isNormal ?????????????????????
         */
        @Override
        public void onStreamEnd(boolean isNormal) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onStreamEnd:" + Thread.currentThread().getName() + ",isNormal:" + isNormal);
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveVideoListener != null) {
                dwLiveVideoListener.onStreamEnd(isNormal);
            }
            if (dwLiveRoomListener != null) {
                String reason = isNormal ? "???????????????" : "???????????????";
                dwLiveRoomListener.onStreamEnd(isNormal, reason);
            }
        }

        @Override
        public void onStreamStart() {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onStreamStart:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveVideoListener != null) {
                dwLiveVideoListener.onStreamStart();
            }
            if (dwLiveRoomListener != null) {
                dwLiveRoomListener.onStreamStart();
            }
        }

        /**
         * ????????????????????????
         *
         * @param chatLogs ???????????????????????????
         */
        @Override
        public void onHistoryChatMessage(ArrayList<ChatMessage> chatLogs) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onHistoryChatMessage:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveChatListener != null) {
                dwLiveChatListener.onHistoryChatMessage(chatLogs);
            }
        }

        /**
         * ????????????
         *
         * @param msg ????????????
         */
        @Override
        public void onPublicChatMessage(ChatMessage msg) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPublicChatMessage:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveChatListener != null) {
                dwLiveChatListener.onPublicChatMessage(msg);
            }
        }

        /**
         * ????????????????????????????????????
         *
         * @param msgStatusJson ??????????????????????????????json
         */
        @Override
        public void onChatMessageStatus(String msgStatusJson) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onChatMessageStatus:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveChatListener != null) {
                dwLiveChatListener.onChatMessageStatus(msgStatusJson);
            }
        }

        /**
         * ??????????????????????????????????????????????????????????????????????????????
         *
         * @param msg ????????????
         */
        @Override
        public void onSilenceUserChatMessage(ChatMessage msg) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onSilenceUserChatMessage:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveChatListener != null) {
                dwLiveChatListener.onSilenceUserChatMessage(msg);
            }
        }

        /**
         * ??????????????????
         *
         * @param mode ???????????? 1???????????????  2???????????????
         */
        @Override
        public void onBanChat(int mode) {
//            if (BuildConfig.DEBUG) {
//                Log.d(TAG, "onBanChat:" + Thread.currentThread().getName());
//                ThreadUtils.checkIsOnMainThread();
//                Log.d(TAG, "onBanChat:mode" + mode);
//            }
            if (dwLiveChatListener != null) {
                dwLiveChatListener.onBanChat(mode);
            }
            if (dwLiveRoomListener != null) {
                dwLiveRoomListener.onBanChat(mode);
            }
        }

        /**
         * ????????????????????????
         *
         * @param mode ???????????? 1???????????????  2???????????????
         */
        @Override
        public void onUnBanChat(int mode) {
//            if (BuildConfig.DEBUG) {
//                Log.d(TAG, "onUnBanChat:" + Thread.currentThread().getName());
//                ThreadUtils.checkIsOnMainThread();
//                Log.d(TAG, "onUnBanChat:mode" + mode);
//            }
            if (dwLiveChatListener != null) {
                dwLiveChatListener.onUnBanChat(mode);
            }
            if (dwLiveRoomListener != null) {
                dwLiveRoomListener.onUnBanChat(mode);
            }
        }

        @Override
        public void onBanDeleteChat(String userId) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onBanDeleteChat:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
                Log.d(TAG, "onBanDeleteChat:userId" + userId);
            }
            if (dwLiveChatListener != null) {
                dwLiveChatListener.onBanDeleteChat(userId);
            }
        }

        /**
         * ???????????????
         *
         */
        @Override
        public void onPrivateChat(PrivateChatInfo info) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPrivateChat:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveMoreFunctionListener != null) {
                dwLiveMoreFunctionListener.onPrivateChat(info);
            }
        }

        /**
         * ??????????????????
         *
         */
        @Override
        public void onPrivateChatSelf(PrivateChatInfo info) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPrivateChatSelf:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveMoreFunctionListener != null) {
                dwLiveMoreFunctionListener.onPrivateChatSelf(info);
            }
        }

        /**
         * ????????????<br/>
         * ???????????????15???
         *
         * @param count ????????????
         */
        @Override
        public void onUserCountMessage(int count) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onUserCountMessage:" + Thread.currentThread().getName() + ",userCount:" + count);
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveRoomListener != null) {
                dwLiveRoomListener.showRoomUserNum(count);
            }
        }


        /**
         * ???????????????????????????<br/>
         * ?????????<br/>
         * ??????docTotalPage?????????0???pageNum???1??????<br/>
         * ????????????docTotalPage??????????????????pageNum???0??????
         *
         * @param docId        ??????Id
         * @param docName      ????????????
         * @param pageNum      ????????????
         * @param docTotalPage ???????????????????????????
         */
        @Override
        public void onPageChange(String docId, String docName, int width, int height, int pageNum, int docTotalPage) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPageChange:" + Thread.currentThread().getName() + ",docId:" + docId + ",docName:" + docName + ",pageNum:" + pageNum + ",docTotalPage:" + docTotalPage);
                ThreadUtils.checkIsOnMainThread();
            }

            currentDocId = docId;
            currentPageNum = pageNum;
            totalPageNum = docTotalPage;
        }

        /**
         * ??????
         *
         */
        @Override
        public void onNotification(String msg) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onNotification:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }

        }

        /**
         * ???????????????
         *
         * @param switchInfo ????????????????????? <br>
         * ??????<br>
         * 1. ????????????????????????{"source_type":"10","source_type_desc":"?????????????????????????????????"} <br>
         * 2. ??????????????????????????????????????????????????????????????????
         */
        @Override
        public void onSwitchSource(String switchInfo) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onSwitchSource:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
        }


        /**
         * ????????????????????????(????????????????????????????????????????????????)
         *
         * @param msgs ??????????????????
         */
        @Override
        public void onHistoryBroadcastMsg(ArrayList<BroadCastMsg> msgs) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onHistoryBroadcastMsg:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveChatListener != null) {
                // ?????????
                if (msgs == null) {
                    return;
                }
                // ????????????????????????,?????????????????????????????????????????????????????????
                dwLiveChatListener.onHistoryBroadcastMsg(msgs);

            }
        }


        /**
         * ??????????????????????????????
         *
         * @param msg ???????????? ??????id
         */
        @Override
        public void onBroadcastMsg(BroadCastMsg msg) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onBroadcastMsg2:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (noticeCallback != null) {
                noticeCallback.callBack(msg.getContent());
            }
            if (dwLiveChatListener != null) {
                dwLiveChatListener.onBroadcastMsg(msg);
            }
        }

        /**
         * ??????????????????????????????
         *
         * @param action ???????????? ??????id??????????????????
         */
        @Override
        public void onBroadcastMsgAction(BroadCastAction action) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onBroadcastMsgAction:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveChatListener != null) {
                // ??????????????????
                if (action.getAction() == 1) {
                    dwLiveChatListener.onBroadcastMsgDel(action.getId());
                }

            }
        }

        /**
         * ?????????????????????????????????
         *
         */
        @Override
        public void onInformation(String msg) {
//            if (BuildConfig.DEBUG) {
//                Log.d(TAG, "onInformation:" + Thread.currentThread().getName());
//                ThreadUtils.checkIsOnMainThread();
//            }
//            if (dwLiveRoomListener != null) {
//                dwLiveRoomListener.onInformation(msg);
//            }
        }

        /**
         * ???????????????
         *
         * @param exception  DWLiveException
         */
        @Override
        public void onException(DWLiveException exception) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onException:" + Thread.currentThread().getName() + "," + exception.toString());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                if (exception.getErrorCode() == ErrorCode.GET_PLAY_URL_FAILED) {
                    dwLiveFunctionListener.onException("????????????????????????:" + exception.getMessage());
                } else if (exception.getErrorCode() == ErrorCode.GET_HISTORY_FAILED) {
                    dwLiveFunctionListener.onException("????????????????????????:" + exception.getMessage());
                } else if (exception.getErrorCode() == ErrorCode.DOC_PAGE_INFO_FAILED) {
                    dwLiveFunctionListener.onException("??????????????????");
                } else if (exception.getErrorCode() == ErrorCode.CONNECT_SERVICE_FAILED) {
                    dwLiveFunctionListener.onException("??????socket???????????????:" + exception.getMessage());
                } else {
                    dwLiveFunctionListener.onException("unknown error:" + exception.getMessage());
                }
            }
        }

        /**
         * ??????????????????????????????
         *
         * @param type ?????????????????????<br>
         * 10:??????????????????????????????????????????????????????????????????????????????<br>
         * 20:???????????????????????????????????????????????????????????????
         */
        @Override
        public void onKickOut(int type) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onKickOut:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveRoomListener != null) {
                dwLiveRoomListener.onKickOut();
            }
        }

        /**
         * ?????????????????????
         *
         * @param playedTime ????????????????????????????????????????????????-1
         */
        @Override
        public void onLivePlayedTime(int playedTime) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onLivePlayedTime:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
        }

        /**
         * ?????????????????????????????????
         *
         * @param exception ???????????????????????????
         */
        @Override
        public void onLivePlayedTimeException(Exception exception) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onLivePlayedTimeException:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
        }

        /**
         * ?????????????????????
         *
         */
        @Override
        public void isPlayedBack(boolean isPlayedBack) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "isPlayedBack:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
        }


        /**
         * ????????????
         *
         * @param customMessage customMessage
         */
        @Override
        public void onCustomMessage(String customMessage) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onCustomMessage:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
                Log.d(TAG, "onCustomMessage:" + customMessage);
            }
        }

        /**
         * ??????
         *
         * @param reason ????????????
         */
        @Override
        public void onBanStream(String reason) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onBanStream:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
                Log.d(TAG, "onBanStream:reason" + reason);
            }
            if (dwLiveVideoListener != null) {
                dwLiveVideoListener.onBanStream(reason);
            }
            if (dwLiveRoomListener != null) {
                dwLiveRoomListener.onStreamEnd(true, reason);
            }
        }

        /**
         * ??????
         */
        @Override
        public void onUnbanStream() {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onUnbanStream:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveVideoListener != null) {
                dwLiveVideoListener.onUnbanStream();
            }
        }

        @Override
        public void onInitFinished() {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onInitFinished:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveRoomListener != null && DWLive.getInstance().getRoomInfo() != null) {
                dwLiveRoomListener.showRoomTitle(DWLive.getInstance().getRoomInfo().getName());
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onInitFinished();
            }

        }

        @Override
        public void onHDAudioMode(DWLive.LiveAudio hasAudio) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onHDAudioMode:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveRoomListener != null) {
                dwLiveRoomListener.onHDAudioMode(hasAudio);
            }
        }

        @Override
        public void onHDReceivedVideoQuality(List<LiveQualityInfo> videoQuality, LiveQualityInfo currentQuality) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onHDReceivedVideoQuality:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveRoomListener != null) {
                dwLiveRoomListener.onHDReceivedVideoQuality(videoQuality, currentQuality);
            }
        }

        @Override
        public void onHDReceivedVideoAudioLines(List<LiveLineInfo> lines, int indexNum) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onHDReceivedVideoAudioLines:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveRoomListener != null) {
                dwLiveRoomListener.onHDReceivedVideoAudioLines(lines, indexNum);
            }
        }

        /**
         * ??????
         *
         * @param isRemove     ?????????????????????????????????true????????????????????????announcement?????????null
         * @param announcement ????????????
         */
        @Override
        public void onAnnouncement(boolean isRemove, String announcement) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onAnnouncement:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (!isRemove) {
                noticeCallback.callBack(announcement);
            }
            if (dwLiveMoreFunctionListener != null) {
                dwLiveMoreFunctionListener.onAnnouncement(isRemove, announcement);
            }
        }

        /**
         * ????????????
         *
         * @param duration ?????????????????????????????????
         */
        @Override
        public void onRollCall(int duration) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onRollCall:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onRollCall(duration);
            }
        }

        /**
         * ????????????
         *
         * @param lotteryId ???????????????id
         */
        @Override
        public void onStartLottery(String lotteryId) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onStartLottery:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onStartLottery(lotteryId);
            }
        }

        /**
         * ????????????
         *
         * @param isWin       ???????????????true???????????????
         * @param lotteryCode ?????????
         * @param lotteryId   ???????????????id
         * @param winnerName  ??????????????????
         */
        @Override
        public void onLotteryResult(boolean isWin, String lotteryCode, String lotteryId, String winnerName) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onLotteryResult:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onLotteryResult(isWin, lotteryCode, lotteryId, winnerName);
            }
        }

        /**
         * ????????????
         *
         * @param lotteryId ???????????????id
         */
        @Override
        public void onStopLottery(String lotteryId) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onStopLottery:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onStopLottery(lotteryId);
            }
        }

        /**
         * ??????2.0
         */
        @Override
        public void onLottery(LotteryAction lotteryAction) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onLottery:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onLottery(lotteryAction);
            }
        }

        /**
         * ????????????
         *
         * @param voteCount ?????????????????????2-5
         * @param VoteType  0???????????????1?????????????????????????????????
         */
        @Override
        public void onVoteStart(int voteCount, int VoteType) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onVoteStart:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onVoteStart(voteCount, VoteType);
            }
        }

        /**
         * ????????????
         */
        @Override
        public void onVoteStop() {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onVoteStop:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onVoteStop();
            }
        }

        /**
         * ??????????????????
         *
         * @param jsonObject ??????????????????
         */
        @Override
        public void onVoteResult(JSONObject jsonObject) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onVoteResult:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onVoteResult(jsonObject);
            }
        }

        /**
         * ????????????????????????
         *
         * @param type       ????????????: 1 ?????? 2 ??????(??????????????????)
         * @param viewerId   ????????????id
         * @param viewerName ??????????????????
         */
        @Override
        public void onPrizeSend(int type, String viewerId, String viewerName) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPrizeSend:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onPrizeSend(type, viewerId, viewerName);
            }
        }

        /**
         * ????????????
         *
         * @param info ????????????
         */
        @Override
        public void onQuestionnairePublish(QuestionnaireInfo info) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onQuestionnairePublish:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null && info != null) {
                dwLiveFunctionListener.onQuestionnairePublish(info);
            }
        }

        /**
         * ????????????
         *
         * @param questionnaireId ??????Id
         */
        @Override
        public void onQuestionnaireStop(String questionnaireId) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onQuestionnaireStop:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onQuestionnaireStop(questionnaireId);
            }
        }

        /**
         * ??????????????????
         *
         */
        @Override
        public void onQuestionnaireStatis(QuestionnaireStatisInfo info) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onQuestionnaireStatis:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onQuestionnaireStatis(info);
            }
        }

        /**
         * ?????????????????????
         *
         * @param title       ????????????
         * @param externalUrl ?????????????????????
         */
        @Override
        public void onExeternalQuestionnairePublish(String title, String externalUrl) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onExeternalQuestionnairePublish:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onExeternalQuestionnairePublish(title, externalUrl);
            }
        }

        /**
         * ???????????????
         *
         * @param info ???????????????
         */
        @Override
        public void onPracticePublish(PracticeInfo info) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPracticePublish:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onPracticePublish(info);
            }
        }

        /**
         * ???????????????????????????
         *
         * @param info ???????????????
         */
        @Override
        public void onPracticeSubmitResult(PracticeSubmitResultInfo info) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPracticeSubmitResult:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onPracticeSubmitResult(info);
            }
        }

        /**
         * ???????????????????????????
         *
         * @param info ?????????????????????
         */
        @Override
        public void onPracticStatis(PracticeStatisInfo info) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPracticStatis:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onPracticStatis(info);
            }
        }

        @Override
        public void onPracticRanking(PracticeRankInfo info) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPracticRanking:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onPracticRanking(info);
            }
        }

        /**
         * ?????????????????????
         *
         * @param practiceId ?????????ID
         */
        @Override
        public void onPracticeStop(String practiceId) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPracticeStop:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onPracticeStop(practiceId);
            }
        }

        /**
         * ?????????????????????
         *
         * @param practiceId ?????????ID
         */
        @Override
        public void onPracticeClose(String practiceId) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPracticeClose:" + Thread.currentThread().getName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (dwLiveFunctionListener != null) {
                dwLiveFunctionListener.onPracticeClose(practiceId);
            }
        }

        /**
         *    ????????????,?????????????????????????????????????????????
         * @param banChatBroadcast
         *    userId ??????id
         *    userName ?????????
         *    userRole ????????????
         *    userAvatar ????????????
         *    groupId ??????id
         */
        @Override
        public void HDBanChatBroadcastWithData(BanChatBroadcast banChatBroadcast) {
//            if (BuildConfig.DEBUG) {
//                Log.d(TAG, "HDBanChatBroadcastWithData:" + Thread.currentThread().getName());
//                ThreadUtils.checkIsOnMainThread();
//            }
//            if (dwLiveChatListener != null) {
//                dwLiveChatListener.HDBanChatBroadcastWithData(banChatBroadcast);
//            }
        }

        /**
         *  ??????????????????
         * {@link UserRedminAction.ActionType#HDUSER_IN_REMIND} ???????????????
         * {@link UserRedminAction.ActionType#HDUSER_OUT_REMIND}   ???????????????
         *
         * @param userJoinExitAction
         *    userId ??????id
         *    userName ?????????
         *    userRole ????????????
         *    userAvatar ????????????
         *    groupId ??????id
         *    role ???????????????  1-?????????2-?????????3-????????????4-?????????
         *    content ???????????????
         */
        @Override
        public void HDUserRemindWithAction(UserRedminAction userJoinExitAction) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "HDUserRemindWithAction:" + Thread.currentThread().getName() + ",userId = " + userJoinExitAction.getUserId() + ",userName=" + userJoinExitAction.getUserName());
                ThreadUtils.checkIsOnMainThread();
            }
            if (userListener != null) {
                userListener.HDUserRemindWithAction(userJoinExitAction);
            }
        }
    };


    //************************************ ?????????????????? ***************************************/


    public void initRtc(CCRTCRender localRender, CCRTCRender remoteRender) {
        DWLive dwLive = DWLive.getInstance();
        if (dwLive != null) {
            dwLive.setRtcClientParameters(rtcClientListener, localRender, remoteRender);
        }
    }


    private boolean isAllowRtc = false;

    /**
     * ????????????????????????
     */
    public boolean isAllowRtc() {
        return isAllowRtc;
    }


    private boolean isRtcing = false;
    private boolean mIsVideoRtc = false;

    /**
     * ???????????????????????????
     */
    public boolean isRtcing() {
        return isRtcing;
    }

    /**
     * ?????????????????????????????????
     *
     * @return true ??? false ???
     */
    public boolean isVideoRtc() {
        return mIsVideoRtc;
    }

    /**
     * ????????????
     *
     * @param videoRtc ?????????????????????
     */
    public void startRTCConnect(boolean videoRtc) {
        // ??????????????????????????????????????????????????????????????????????????????
        if (!isAllowRtc) {
            return;
        }
        if (videoRtc) {
            DWLive.getInstance().startRtcConnect();
        } else {
            DWLive.getInstance().startVoiceRTCConnect();
        }
    }

    /**
     * ??????????????????
     */
    public void cancelRTCConnect() {
        DWLive.getInstance().disConnectApplySpeak();
        isRtcing = false;
        // ??????
        if (dwLiveRTCStatusListener != null) {
            dwLiveRTCStatusListener.onCloseSpeak();
        }
    }

    //------------------------------ ???????????????????????????????????? --------------------------------/

    private final RtcClient.RtcClientListener rtcClientListener = new RtcClient.RtcClientListener() {

        @Override
        public void onAllowSpeakStatus(final boolean isAllowSpeak) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onAllowSpeakStatus:" + Thread.currentThread().getName() + ",isAllowSpeak = " + isAllowSpeak);
                ThreadUtils.checkIsOnMainThread();
            }
            isAllowRtc = isAllowSpeak;

            if (!isAllowSpeak) {
                if (dwLiveRTCStatusListener != null) {
                    dwLiveRTCStatusListener.onCloseSpeak();
                }
            }
        }

        /**
         * ?????????????????????,??????
         * @param videoSize ????????????????????????"600x400"
         */
        @Override
        public void onEnterSpeak(boolean isVideoRtc, boolean needAdjust, String videoSize) {
            mIsVideoRtc = isVideoRtc;
            isRtcing = true;
            if (dwLiveRTCListener != null) {
                dwLiveRTCListener.onEnterSpeak(isVideoRtc, needAdjust, videoSize);
            }
            if (dwLiveRTCStatusListener != null) {
                dwLiveRTCStatusListener.onEnterRTC(isVideoRtc);
            }

        }

        @Override
        public void onDisconnectSpeak() {
            if (dwLiveRTCListener != null && isRtcing) {
                dwLiveRTCListener.onDisconnectSpeak();
            }
            if (dwLiveRTCStatusListener != null) {
                dwLiveRTCStatusListener.onExitRTC();
            }
            isRtcing = false;
        }

        @Override
        public void onSpeakError(final Exception e) {
            if (dwLiveRTCListener != null) {
                dwLiveRTCListener.onSpeakError(e);
            }
            if (dwLiveRTCStatusListener != null) {
                dwLiveRTCStatusListener.onExitRTC();
            }
            isRtcing = false;
        }

        @Override
        public void onCameraOpen(final int width, final int height) {

        }
    };

    //------------------------------ ?????????????????? --------------------------------/
    private String currentDocId;
    private int currentPageNum;
    private int totalPageNum;

    // ????????????DocId
    public String getCurrentDocId() {
        return currentDocId;
    }

    // ??????????????????
    public int getCurrentPageNum() {
        return currentPageNum;
    }

    // ??????????????????
    public int getTotalPageNum() {
        return totalPageNum;
    }

    private NoticeCallback noticeCallback;

    public void setNoticeCallback(NoticeCallback callback) {
        this.noticeCallback = callback;
    }

    public interface NoticeCallback {
        void callBack(String content);
    }
}
