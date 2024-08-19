package com.semicolon.africa.Services;

import com.semicolon.africa.Data.Model.User;
import com.semicolon.africa.Dto.*;

import java.util.List;

public interface AdminServices {
    AdminRegisterResponse registerAdmin(AdminRegisterRequest registerRequest);
    AdminDisableAccountResponse accountDisableAdmin(AdminDisableAccountRequest adminDisableAccountRequest);

}
