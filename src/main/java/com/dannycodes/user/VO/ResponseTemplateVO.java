package com.dannycodes.user.VO;

import com.dannycodes.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplateVO {


    private User user;
    private Department department;




}
