package com.kainos.ea.validator;

import com.kainos.ea.exception.InvalidJobRoleException;
import com.kainos.ea.models.AddJobRole;

public class JobRoleValidator {
    public static boolean isValidJobRole(AddJobRole jobRole) throws InvalidJobRoleException {

        if (jobRole.getRoleName().length() > 50) {
            throw new InvalidJobRoleException("Role name is too long");
        }
        if (jobRole.getSpecification().length() > 1500) {
            throw new InvalidJobRoleException("Role spec is too long");
        }
        if (jobRole.getLink().length() > 1000) {
            throw new InvalidJobRoleException("URL too long");
        }
        if (jobRole.getResponsibility().length() > 200) {
            throw new InvalidJobRoleException("responsibility too long");
        }
        return true;
    }
}
