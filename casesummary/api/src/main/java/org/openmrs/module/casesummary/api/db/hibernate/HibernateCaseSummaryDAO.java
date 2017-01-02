/**
 * The contents of this file are subject to the OpenMRS Public License Version
 * 1.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 *
 * Copyright (C) OpenMRS, LLC. All Rights Reserved.
 */
package org.openmrs.module.casesummary.api.db.hibernate;

import java.util.List;
import org.hibernate.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.casesummary.api.db.CaseSummaryDAO;
import org.openmrs.module.casesummary.model.DoctorProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.casesummary.model.OtNote;
import org.openmrs.module.casesummary.model.PatientSearchCs;
import org.openmrs.module.casesummary.model.SailentFeature;
import org.openmrs.module.casesummary.model.SelectPatient;

/**
 * It is a default implementation of {@link CaseSummaryDAO}.
 */
@Repository
public class HibernateCaseSummaryDAO implements CaseSummaryDAO {

    protected final Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public DoctorProfile saveDocPro(DoctorProfile doctorProfile) throws DAOException {
        sessionFactory.getCurrentSession().saveOrUpdate(doctorProfile);
        return doctorProfile;
    }

    @Override
    public DoctorProfile docProFindByUserId(int userId) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DoctorProfile.class);
        criteria.add(Restrictions.eq("user.userId", userId));
        return (DoctorProfile) criteria.uniqueResult();
    }

    @Override
    public List<PatientSearchCs> patientSearchByIdName(String searchKey) throws DAOException {
        String hql = null;
        hql = "from PatientSearch ps where ps.identifier LIKE '%"
                + searchKey
                + "%' "
                // + "OR ps.fullname LIKE '%" + searchKey + "%')";
                + "OR ps.fullname LIKE '%" + searchKey + "%')";

        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery(hql);
        q.setFirstResult(0);
        q.setMaxResults(50);

        List<PatientSearchCs> list = q.list();
        return list;
    }

    @Override
    public PatientSearchCs getPatientSerByPatientId(int id) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PatientSearchCs.class);
        criteria.add(Restrictions.eq("patientId", id));
        return (PatientSearchCs) criteria.uniqueResult();
    }

    @Override
    public SelectPatient saveSlectPatient(SelectPatient selectPatient) throws DAOException {
        sessionFactory.getCurrentSession().saveOrUpdate(selectPatient);
        return selectPatient;
    }

    @Override
    public List<SelectPatient> listSelectPatientByUser(int userId) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SelectPatient.class);
        criteria.add(Restrictions.eq("userId.userId", userId));
        return criteria.list();
    }

    @Override
    public SelectPatient getSelectPatiByPatientIdUsreId(int userId, int patientId) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SelectPatient.class);
        criteria.add(Restrictions.eq("userId.userId", userId));
        criteria.add(Restrictions.eq("patientId.personId", patientId));
        return (SelectPatient) criteria.uniqueResult();

    }

    @Override
    public SailentFeature saveSailentFeat(SailentFeature sailentFeature) throws DAOException {
        sessionFactory.getCurrentSession().saveOrUpdate(sailentFeature);
        return sailentFeature;
    }

    @Override
    public SelectPatient getSelPatientById(int id) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SelectPatient.class);
        criteria.add(Restrictions.eq("id", id));
        return (SelectPatient) criteria.uniqueResult();
    }

    @Override
    public SailentFeature getSailentById(int id) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SailentFeature.class);
        criteria.add(Restrictions.eq("selectPatient.id", id));
        return (SailentFeature) criteria.uniqueResult();
    }

    @Override
    public OtNote saveOtNote(OtNote otNote) throws DAOException {
        sessionFactory.getCurrentSession().saveOrUpdate(otNote);
        return otNote;
    }

    @Override
    public List<OtNote> listOtNote(int id) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OtNote.class);
        criteria.add(Restrictions.eq("selectPatient.id", id));
        return criteria.list();
    }

}
