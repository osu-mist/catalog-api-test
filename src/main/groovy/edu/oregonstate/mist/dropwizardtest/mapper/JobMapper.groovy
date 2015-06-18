package edu.oregonstate.mist.dropwizardtest.mapper

import edu.oregonstate.mist.dropwizardtest.core.Job

import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet
import java.sql.SQLException

/* map database columns to job properties */
public class JobMapper implements ResultSetMapper<Job> {
    public Job map(int i, ResultSet rs, StatementContext sc) throws SQLException {
        Job job = new Job()

        job.with {
            id              = rs.getLong   'PYVPASJ_PIDM'
            positionNumber  = rs.getString 'PYVPASJ_POSN'
            suffix          = rs.getString 'PYVPASJ_SUFF'
            status          = rs.getString 'PYVPASJ_STATUS'
            jobTitle        = rs.getString 'PYVPASJ_DESC'
            eclsCode        = rs.getString 'PYVPASJ_ECLS_CODE'
            appointmentType = rs.getString 'PYVPASJ_APPOINTMENT_TYPE'
            beginDate       = rs.getDate   'PYVPASJ_BEGIN_DATE'
            endDate         = rs.getDate   'PYVPASJ_END_DATE'
            pclsCode        = rs.getString 'PYVPASJ_PCLS_CODE'
            salGrade        = rs.getString 'PYVPASJ_SAL_GRADE'
            salStep         = rs.getLong   'PYVPASJ_SAL_STEP'
            orgnCodeTs      = rs.getString 'PYVPASJ_ORGN_CODE_TS'
            orgnDesc        = rs.getString 'PYVPASJ_ORGN_DESC'
            bctrTitle       = rs.getString 'PYVPASJ_BCTR_TITLE'
            supervisorPidm  = rs.getLong   'PYVPASJ_SUPERVISOR_PIDM'
            supervisorPosn  = rs.getString 'PYVPASJ_SUPERVISOR_POSN'
            supervisorSuff  = rs.getString 'PYVPASJ_SUPERVISOR_SUFF'
            trialInd        = rs.getLong   'PYVPASJ_TRIAL_IND'
            annualInd       = rs.getLong   'PYVPASJ_ANNUAL_IND'
            evalDate        = rs.getDate   'PYVPASJ_EVAL_DATE'
            low             = rs.getLong   'PYVPASJ_LOW'
            midpoint        = rs.getLong   'PYVPASJ_MIDPOINT'
            high            = rs.getLong   'PYVPASJ_HIGH'
            salary          = rs.getLong   'PYVPASJ_SALARY'
            sgrpCode        = rs.getString 'PYVPASJ_SGRP_CODE'
        }

        return job
    }
}
