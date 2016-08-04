package com.netcracker.solutions.kpi.persistence.repository;

import com.netcracker.solutions.kpi.persistence.model.SocialInformation;
import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;
import com.netcracker.solutions.kpi.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SocialInformationRepository extends JpaRepository<SocialInformation, Long> {

    @Query(value = "SELECT si.id, si.id_social_network, si.id_user, si.access_info, sn.title, si.id_user_in_social_network \n" +
            "FROM social_information si\n" +
            " INNER JOIN social_network sn ON sn.id = si.id_social_network \n" +
            "WHERE si.id_user = ?1", nativeQuery = true)
    Set<SocialInformation> getByUserId(Long id);

    @Query(value = "INSERT INTO social_information (access_info, id_user, id_social_network, id_user_in_social_network) " +
            " VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    Long insertSocialInformation(String accessInfo, Long userId, Long socialNetworkId, Long idUserInSocialNetwork);

    @Query(value = "UPDATE social_information set access_info = ?1 WHERE social_information.id = ?2", nativeQuery = true)
    int updateSocialInformation(String accessInfo, Long id);

    @Query(value = "UPDATE public.social_information SET access_info = ?1 \n" +
            " WHERE id_social_network = ?2 AND id_user_in_social_network = ?3 ;", nativeQuery = true)
    int updateSocialInformation(Long idNetwork, Long idUser, String info);

    @Query(value = "SELECT si.id, si.access_info, si.id_user, si.id_social_network, sn.title, si.id_user_in_social_network \n" +
            "             FROM public.social_information si JOIN public.social_network sn " +
            "               ON si.id_social_network = sn.id " +
            "         WHERE si.id_social_network = ?1 and si.id_user_in_social_network = ?2;", nativeQuery = true)
    SocialInformation getByIdUserInSocialNetworkSocialType(Long idUserInSocialNetwork, Long idSocialNetwork);

    @Query(value = "SELECT si.id, si.id_social_network, si.id_user, si.access_info,\n" +
            " sn.title, si.id_user_in_social_network FROM public.user u JOIN public.social_information si ON u.id = si.id_user \n" +
            "JOIN public.social_network sn ON si.id_social_network = sn.id WHERE u.email = ?1 AND si.id_social_network = ?2;",
            nativeQuery = true)
    SocialInformation getByUserEmailSocialNetworkType(String email, Long socialNetworkId);

    @Query(value = "SELECT EXISTS( SELECT si.id, si.access_info, si.id_user, si.id_social_network, sn.title, si.id_user_in_social_network " +
            "FROM public.social_information si JOIN public.social_network sn ON si.id_social_network = sn.id " +
            "WHERE si.id_social_network = ?1 and si.id_user_in_social_network = ?2)", nativeQuery = true)
    boolean isExist(Long idUserInSocialNetwork, Long idSocialNetwork);


    // TODO: 04.08.2016
    @Query(value = "select * from  social_information", nativeQuery = true)
    boolean isExist(String email, Long idSocialNetwork);

}
