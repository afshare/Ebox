package com.allen.dao.socketDao;

import com.allen.model.FacilityEworldState;



public interface FacilityStateDAO {
	public boolean ChangeToOnline(FacilityEworldState fstate) throws Exception;
	public boolean ChangeRecentTime(FacilityEworldState fstate) throws Exception;
	public boolean ChangeToOffline(FacilityEworldState fstate) throws Exception;
	public boolean AddFacWithOnline(FacilityEworldState fstate) throws Exception;
	public boolean DeleteFacOnline(FacilityEworldState fstate) throws Exception;
	public boolean SelectFac(FacilityEworldState fstate) throws Exception;
}
