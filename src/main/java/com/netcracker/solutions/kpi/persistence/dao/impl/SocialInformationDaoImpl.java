package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.SocialInformationDao;
import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;
import com.netcracker.solutions.kpi.persistence.model.impl.proxy.UserProxy;
import com.netcracker.solutions.kpi.persistence.model.impl.real.SocialInformationImpl;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class SocialInformationDaoImpl implements SocialInformationDao {
    private static Logger log = LoggerFactory.getLogger(SocialInformationDaoImpl.class.getName());

    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;

    /*public SocialInformationDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }*/

    private ResultSetExtractor<SocialInformation> extractor = resultSet -> {
        long socialInformationId = resultSet.getLong("id");
        SocialInformation socialInformation;
        socialInformation = new SocialInformationImpl(socialInformationId,
                resultSet.getString("access_info"), new User(resultSet.getLong("id_user")),
                new SocialNetwork(resultSet.getLong("id_social_network"), resultSet.getString("title")),resultSet.getLong("id_user_in_social_network"));
        return socialInformation;
    };

    private static final String SQL_GET_BY_ID = "SELECT si.id, si.access_info, si.id_user, si.id_social_network, sn.title, si.id_user_in_social_network " +
            "FROM public.social_information si JOIN public.social_network sn ON si.id_social_network = sn.id WHERE si.id = ?;";

    private static final String SQL_GET_BY_USER_ID = "SELECT si.id, si.id_social_network, si.id_user, si.access_info, sn.title, si.id_user_in_social_network "
            + "FROM \"social_information \" si" + "\n INNER JOIN social_network sn ON sn.id = si.id_social_network "
            + "WHERE si.id_user = ?";

    private static final String SQL_INSERT = "INSERT INTO social_information (access_info, id_user, id_social_network, id_user_in_social_network) VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE social_information set access_info = ? WHERE social_information.id = ?";

    private static final String SQL_DELETE = "DELETE FROM \"social_information\" WHERE \"social_information\".id = ?";

    private static final String SQL_GET_BY_USER_EMAIL_SOCIAL_TYPE = "SELECT si.id, si.id_social_network, si.id_user, si.access_info," +
            " sn.title, si.id_user_in_social_network FROM public.user u JOIN public.social_information si ON u.id = si.id_user " +
            "JOIN public.social_network sn ON si.id_social_network = sn.id WHERE u.email = ? AND si.id_social_network = ?;";

    private static final String SQL_GET_BY_ID_IN_SOCIAL_NETWORK_AND_SOCIAL_TYPE = "SELECT si.id, si.access_info, si.id_user, si.id_social_network, sn.title, si.id_user_in_social_network " +
            "FROM public.social_information si JOIN public.social_network sn ON si.id_social_network = sn.id WHERE si.id_social_network = ? and si.id_user_in_social_network = ?;";

    private static final String SQL_EXIST_ID_IN_SOCIAL_NETWORK_AND_SOCIAL_TYPE = "SELECT EXISTS( SELECT si.id, si.access_info, si.id_user, si.id_social_network, sn.title, si.id_user_in_social_network \" +\n" +
            "            \"FROM public.social_information si JOIN public.social_network sn ON si.id_social_network = sn.id WHERE si.id_social_network = ? and si.id_user_in_social_network = ?);";

    private static final String SQL_UPDATE_SOCIAL_INFORMATION = "UPDATE public.social_information SET access_info = ?" +
            " WHERE id_social_network = ? AND id_user_in_social_network = ?;";

    @Override
    public SocialInformation getById(Long id) {
        log.info("Looking for social information with id = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
    }

    @Override
    public Set<SocialInformation> getByUserId(Long id) {
        log.info("Looking for social informations with id_user = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryForSet(SQL_GET_BY_USER_ID, extractor, id);
    }

    @Override
    public Long insertSocialInformation(SocialInformation socialInformation, User user, SocialNetwork socialNetwork) {
        log.info("Inserting social information with id_user, id_social_network  = {}, {}", user.getId(),
                socialNetwork.getId());
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT, socialInformation.getAccessInfo(), user.getId(),
                socialNetwork.getId(), socialInformation.getIdUserInSocialNetwork());
    }

    @Override
    public int updateSocialInformation(SocialInformation socialInformation) {
        log.info("Updating social_information with id = {}", socialInformation.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_UPDATE, socialInformation.getAccessInfo(), socialInformation.getId());
    }

    @Override
    public int deleteSocialInformation(SocialInformation socialInformation) {
        log.info("Deleting social information with id = ", socialInformation.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_DELETE, socialInformation.getId());
    }



//    @Override
//    public boolean isExist(String email, Long idSocialNetwork) {
//        log.trace("Search user social information by email = {}", email);
//        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_USER_EMAIL_SOCIAL_TYPE, extractor, email, idSocialNetwork) != null;
//    }

    @Override
    public boolean isExist(String email, Long idSocialNetwork) {
        log.info("Search user social information by email = {}", email);
        return getByUserEmailSocialType(email, idSocialNetwork) != null;
    }

    @Override
    public SocialInformation getByIdUserInSocialNetworkSocialType(Long idUserInSocialNetwork, Long idSocialNetwork) {
        log.info("Search user social information by Id User In Social Network and Social Type = {}, {}",idUserInSocialNetwork,idSocialNetwork);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID_IN_SOCIAL_NETWORK_AND_SOCIAL_TYPE, extractor,idSocialNetwork,idUserInSocialNetwork);
    }

    @Override
    public boolean isExist(Long idUserInSocialNetwork, Long idSocialNetwork) {
        log.info("Search user exists social information by Id User In Social Network and Social Type = {}, {}",idUserInSocialNetwork,idSocialNetwork);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_EXIST_ID_IN_SOCIAL_NETWORK_AND_SOCIAL_TYPE, resultSet -> resultSet.getBoolean(1),idSocialNetwork,idUserInSocialNetwork );
    }

    @Override
    public int updateSocialInformation(Long idNetwork, Long idUser, String info) {
        log.info("Update user social information by Id User In Social Network and Social Type = {}, {}", idUser, idNetwork);
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_UPDATE_SOCIAL_INFORMATION, info, idNetwork, idUser);
    }

    @Override
    public SocialInformation getByUserEmailSocialType(String email, Long idSocialType) {
        List<SocialInformation> list = jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_BY_USER_EMAIL_SOCIAL_TYPE, extractor, email, idSocialType);
        if (list.isEmpty()){
            return null;
        } else if (list.size() <= 1){
            return list.iterator().next();
        } else {
            throw new  IllegalStateException("Data base error, more then one social information for one user for one social network");
        }
    }
}
