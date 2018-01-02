package com.ppuser.client.common;

/**
 * 访问链接
 *
 * @author huangfucai
 */
public class CommonUrl {
    /**
     * 测试：https://papi.ppyoupei.com
     * 正式：https://api.ppyoupei.com
     *
     * 网站根目录
     */
    public final static String SERVER_ROOT = "https://papi.ppyoupei.com/index_AES.php";

    /**
     * 分享内容中---URL
     */
    public final static String SHARE_URL = "https://api.ppyoupei.com/h5static/currency/html/detail_activity.html?id=";

    /**
     * 游记列表
     */
    public final static String YOUJI_LIST = "https://api.ppyoupei.com/h5static/skill/findtravel.html?";

    /**
     * 注册
     */
    public final static String REGISTER = "Client_Register.go";

    /**
     * 注册验证码
     */
    public final static String REGISTER_CODE = "Public_VerifyCode.SendRegCode";

    /**
     * 注册验证码(第三方登陆)
     */
    public final static String BIND_CODE = "Public_VerifyCode.SendBindCode";

    /**
     * 登录
     */
    public final static String LOGIN = "Client_Login.go";

    /**
     * 退出登录
     */
    public final static String LOGINOUT = "Client_Login.Logout";

    /**
     * 忘记密码
     */
    public final static String FORPASSWORD = "Client_UpdatePassword.go";

    /**
     * 忘记密码验证码
     */
    public final static String FORPASSWORD_CODE = "Public_VerifyCode.SendResetPwCode";

    /**
     * 获取用户资料
     */
    public final static String GETUSERINFO = "Client_GetUserInfo.go";

    /**
     * 修改用户资料
     */
    public final static String UPDATEUSERINFO = "Client_UpdateUserInfo.go";

    /**
     * 获取城市列表
     */
    public final static String GETCITILIST = "Public_City.getlist";

    /**
     * 支付
     */
    public final static String PAY = "Client_Activity.activityPay";

    /**
     * 行程详情-支付
     */
    public final static String TRAVELPAY = "Client_Trip.tripPay";

    /**
     * 支付成功
     */
    public final static String PAYSUCCESS = "Client_Activity.activityPayResult";

    /**
     * 投诉
     */
    public final static String COMPLAINT = "Client_Compain.tousuinto";

    /**
     * 上传手机唯一key
     */
    public final static String JPUSHKEY = "Public_Push.update";

    /**
     * 获取活动列表
     */
    public final static String GETACTIVITYLIST = "Client_Activity.getuseractivitylist";

    /**
     * 获取行程列表
     */
    public final static String GETROUTELIST = "client_Trip.triplist";

    /**
     * 取消订单
     */
    public final static String CANCELORDER = "Client_Yueqi.yueqi_cancel";

    /**
     * 接受陪游
     */
    public final static String ACCEPTPEIYOU = "Client_Yueqi.yueqi_agree";

    /**
     * 约起发布
     */
    public final static String YUEQIPUBLISH = "Client_Yueqi.yueqi";

    /**
     * 约起发布（带车）
     */
    public final static String YUEQIPUBLISHWITH = "Client_Yueqi.yueqi_car";

    /**
     * 约起发布成功列表
     */
    public final static String YUEQISUCCESSLIST = "Client_Yueqi.yueqi_success";

    /**
     * 选择陪游
     */
    public final static String SELECTPEIYOU = "Client_Yueqi.yueqi_select";

    /**
     * 预约陪游
     */
    public final static String YUYUEPEIYOU = "Client_Yueqi.yueqi_peiyouinput";

    /**
     * 预约陪游提交
     */
    public final static String YUYUEPEIYOUSUBMIT = "Client_Yueqi.yueqi_peiyou";

    /**
     * 搜索陪游
     */
    //// TODO: 2017/5/18 废弃
    public final static String SEARCHPEIYOULIST = "Client_Accompany.getlist";

    /**
     * 搜索活动
     */
    public final static String SEARCHHUODONGLIST = "Client_Activity.getlist";

    /**
     * 实名认证
     */
    public final static String AUTONYM = "Client_MenberAudit.apply";

    /**
     * 活动报名
     */
    public final static String APPLY = "Client_Activity.apply";

    /**
     * 获取实名认证信息
     */
    public final static String GETAUTONYMINFO = "Client_MenberAudit.getLastInfo";

    /**
     * 获取行程详情
     */
    public final static String GETAPPLYINFO = "Client_Trip.tripinfo";

    /**
     * 获取详情信息
     */
    public final static String GETDETAILINFO = "Client_Xianluinfo.xianluinfo";

    /**
     * 行程详情-支付
     */
    public final static String TRACELDETAILSPAY = "Client_Trip.tripPayInfo";

    /**
     * 获取对方融云id
     */
    public final static String GET_CHAT_ID = "Public_Chat.getMemberChatId";
    /**
     * 获取消息（系统）列表
     */
    public final static String GETMAILLIST = "Client_Mail.getMailList";

    /**
     * 评价=提交接口
     */
    public final static String GET_EVALUATE_DATA = "Client_Evaluate.u_evaluateinto";

    /**
     * 行程支付成功
     */
    public final static String GET_TRIP_PAY_RESULT = "Client_Trip.tripPayResult";

    /**
     * 获取红包首页
     */
    public final static String GET_RED_INDEX = "Redpacket_Redpacket.getIndexURL";


    public final static String GET_YUEQI_CANCEL = "Client_Yueqi.yueqi_cancel";
    /**
     * 行程关闭
     */
    public final static String GET_YUEQI_CLOSE = "Client_Trip.closeservice";
    /**
     * 获取发现首页
     */
    public final static String GET_FINDINDEXLIST = "Find_Find.getFindIndexURL";

    /**
     * 绑定银行卡验证码
     */
    public final static String CARD_CODE = "Public_VerifyCode.SendCreditCode";

    /**
     * 添加银行卡
     */
    public final static String ADD_CARD = "Client_CreditCard.addAccount";

    /**
     * 添加支付宝
     */
    public final static String ADD_ALIPAY = "Client_CreditCard.addAlipayAccount";

    /**
     * 获取用户资料
     */
    public final static String GETWALLETINFO = "Client_Wallet.getBalance";

    /**
     * 金币转余额
     */
    public final static String GETBALANCETOCOIN = "Client_Wallet.BalanceToCoin";

    /**
     * 获取银行卡信息
     */
    public final static String GETACCOUNT = "Client_CreditCard.getAccount";


    /**
     * 获取关注好友
     */
    public final static String GET_FRIEND = "Public_Chat.getFocusList";

    /**
     * 添加关注
     */
    public final static String GET_ADD_FRIEND = "Public_Chat.addFocus";

    /**
     * 删除关注
     */
    public final static String GET_DELETE_FRIEND = "Public_Chat.delteFocus";

    /**
     * 获取首页数据
     */
    public final static String GET_INDEX = "Public_UserIndex.userIndex";

    /**
     * 获取首页数据new
     */
    public final static String GET_INDEX_new = "Public_Recommend.getIndex";

    /**
     * 获取发现商品详情
     */
    public final static String GET_FIND_DETAIL = "Find_Find.getinfo";

    /**
     * 获取金币
     */
    public final static String GET_WELLET = "Client_Wallet.getCoin";

    /**
     * 创建发现商品订单
     */
    public final static String GET_ORDER_BUY = "Find_Order.orderBuy";

    /**
     * 获取金币、余额、冻结资金
     */
    public final static String GET_WALLET_BALANCE = "Client_Wallet.getBalance";

    /**
     * 订单支付后，查看详情接口
     */
    public final static String GET_FIND_ORDER_PAY_RESULT = "Find_Order.getOrderPayResult";

    /**
     * 获取分享信息
     */
    public final static String GET_SHARE_ACCOMPANY_INFO = "Public_Share.shareAccompanyInfo";
    /**
     * 判断是否可以领取红包
     */
    public final static String GET_GOLD_COIN_ISRECEIVE = "GoldCoin_GoldCoin.isreceive";
    /**
     * 领取红包
     */
    public final static String GET_GOLD_COIN_RECEIVE = "GoldCoin_GoldCoin.receive";

    /**
     * 获取推荐二维码接口
     */
    public final static String GET_RECOMMEND_QRCODE = "Extension_Qrcode.getQrCode";
    /**
     * 支付宝充值
     */
    public final static String GET_WALLET_RRECHARGE = "Client_Wallet.recharge";

    /**
     * 获取绑定银行卡信息
     */
    public final static String GET_CREDIT_CARD = "Client_CreditCard.getAccount";

    /**
     * 获取支付宝信息
     */
    public final static String GET_CREDIT_ALIPAY = "Client_CreditCard.getAlipayAccount";

    /**
     * 提现
     */
    public final static String GET_WALLET_WITHDRAW = "Client_Wallet.Withdraw";
    /**
     * 商家服务列表
     */
    public final static String GET_ORDER_LIST = "Find_Order.getOrderList";

    /**
     * 意见反馈
     */
    public final static String GET_FEEDBACK = "Client_Feedback.submitFeedBack";

    /**
     * 获取技能列表
     */
    public final static String GET_SKILL_LIST = "Accompany_Skill.getSkillList";
    /**
     * 获取陪游筛选列表
     */
    public final static String GET_ACCOMPANY_lIST = "Client_Select.AccompanyList";
    /**
     * 登录、注册页的法律规定
     */
    public final static String GET_Law_SinglePage = "Public_UserIndex.getSinglePageUrl";

    /**
     * 获取活动筛选列表
     */
    public final static String GET_ACTIVITY_lIST = "Client_Select.ActivityList";
    /**
     * 获取活动详情列表
     */
    public final static String GET_ACTIVITY_INFO = "Client_Xianluinfo.xianluinfo";
    /**
     * 获取交易记录
     */
    public final static String GET_WALLET_LOGS = "Client_Wallet.getLogs";

    /**
     * 获取金币记录
     */
    public final static String GET_WALLET_COINLOGS = "Client_Wallet.getCoinLogs";


    /**
     * 根据融云id 获取用户数据
     */
    public final static String GET_MEMBER_CHAT_BY_IM_ID = "Public_Chat.getMemberChatData";

    /**
     * 根据手机号 获取用户数据
     */
    public final static String GET_MEMBER_CHAT_BY_IM_PHONE = "Public_Chat.getChatDataByPhone";

    /**
     * 根据融云ids 获取用户数据
     */
    public final static String GET_MEMBER_CHAT_BY_IM_IDS = "Public_Chat.getMemberChatInfo";

    /**
     * 获取未读消息（系统）
     */
    public final static String GET_MAIL_UNREAD = "Client_Mail.getMailUnRead";

    /**
     * 标记所有消息为已读
     */
    public final static String SET_READ_ALLMAIL = "Client_Mail.readAllMail";
    /**
     * 查看是否有未付款订单
     */
    public final static String CHECK_UNPAY_ORDER = "Client_Trip.checkUnpayOrder";

    /**
     * 推荐好友接口
     */
    public final static String GET_RECOMMENT_URL = "Public_Share.getRecommentUrl";

    /**
     * 获取商品接口
     */
    public final static String GET_PRODUCT_URL = "http://ppseller.ppyoupei.com/index.php/Home/Index/appLogin?";

    /**
     * 获取游记接口
     */
    public final static String GET_TRAVEL_NOTES = "https://api.ppyoupei.com/h5static/skill/travellist.html?";

    /**
     * 获取写游记接口
     */
    public final static String GET_WIRTE_TRAVEL_NOTES = "https://api.ppyoupei.com/h5static/skill/pubilsh.html?";

    /**
     * 获取个人详情H5 Url
     */
    public final static String GET_PERSONOL_URL = "https://api.ppyoupei.com/h5static/user/html/EscortDetails.html?id=";

    /**
     * 客服页面
     */
    public final static String GET_SERVICE_LIST = "CustomerService_CustomerService.serviceindex";

    /**
     * 用户端填写投诉信息页面
     */
    public final static String GET_COMPAIN_TOUSU = "Client_Compain.tousu";
    /**
     * 意见提交表单
     */
    public final static String GET_FEEDBACK_FORM = "Client_Feedback.getFeedbackForm";

    /**
     * 获取用户实名认证审核状态
     */
    public final static String GET_LAST_INFO = "Accompany_Apply.getLastInfo";

    /**
     * 获取引导页信息
     */
    public final static String GET_GUIDE_INFO = "Public_Adver.getloginAdv";

    /**
     * 第三方登录传递用户信息
     */
    public final static String CLIEN_LOGIN_GOTHIRD = "Client_Login.gothird";

    /**
     * 绑定手机号接口
     */
    public final static String CLIEN_LOGIN_GOBIND = "Client_Login.gobind";

    /**
     * 获取技能列表
     */
    public final static String CLIEN_SKILL_GETSKILLLIST = "client_Skill.getSkillList";


    /**
     * 登录状态下绑定第三方账号
     */
    public final static String CLIEN_LOGIN_HAVELOINGBIND = "Client_Login.haveloginbind";

    /**
     * 获取陪游车辆信息
     */
    public final static String CLIEN_ACCOMPANY_CAR_GETCARINFO = "Accompany_Car.getCarInfo";


    /**
     * 获取游记列表
     */
    public final static String CLIEN_GET_YOUJI_LIST = "Client_Travel.getYouji";

    /**
     * 取收藏列表
     */
    public final static String CLIEN_COLLECT_GET_COLLECT = "Client_Collect.getCollect";

    /**
     * 加收藏
     */
    public final static String CLIEN_COLLECT_ADD_COLLECT = "Client_Collect.addCollect";

    /**
     * 取消收藏
     */
    public final static String CLIEN_COLLECT_DELETE_COLLECT = "Client_Collect.delCollect";

    /**
     * 银联验签
     */
    public final static String PAYNOTFY_UNION_VALIDATEAPP = "PayNotify_Union.validateApp";

}