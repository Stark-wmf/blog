package team.cqupt.blog1.service.impl;

import team.cqupt.blog1.bean.Agree;
import team.cqupt.blog1.dao.IfAgreeDao;
import team.cqupt.blog1.dao.Impl.IfAgreeDaoImpl;
import team.cqupt.blog1.service.IfAgreeService;

public class IfAgreeServiceImpl implements IfAgreeService {
    //service的单例
    private static IfAgreeService instance = null;

    //dao的单例
    private IfAgreeDao ifAgreeDao = null;
    /**
     * 构造方法 实例化时为contentDao赋值
     */
    public IfAgreeServiceImpl() {
        ifAgreeDao = IfAgreeDaoImpl.getInstance();
    }

    public static IfAgreeService getInstance() {
        //双重校验锁
        if (instance == null) {
            synchronized (IfAgreeDaoImpl.class) {
                if (instance == null) {
                    instance = new IfAgreeServiceImpl();
                }
            }
        }
        return instance;
    }
    @Override
    public boolean insertContent(Agree agree) {

        if (agree.getUsername() != null && agree.getTextid() != null) {

            ifAgreeDao.InsertAgree(agree);
            return true;

        }

        return false;

    }

}