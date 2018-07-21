package com.sol.erp.util;

import com.sol.erp.dto.LoginDTO;
import com.sol.erp.dto.ShiftDefinitionDTO;

public class SessionUtil {

	private static LoginDTO sessionLoginInfo;
	private static ShiftDefinitionDTO shiftDefinitionInfo;
	
	public static LoginDTO getLoginInfo() {
		return sessionLoginInfo;
	}

	public static void setLoginInfo(LoginDTO sessionLoginInfo) {
		SessionUtil.sessionLoginInfo = sessionLoginInfo;
	}

    public static ShiftDefinitionDTO getShiftDefinitionInfo() {
        return shiftDefinitionInfo;
    }

    public static void setShiftDefinitionInfo(ShiftDefinitionDTO shiftDefinitionInfo) {
        SessionUtil.shiftDefinitionInfo = shiftDefinitionInfo;
    }

	
}
