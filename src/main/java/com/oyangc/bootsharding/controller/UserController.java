package com.oyangc.bootsharding.controller;

import com.oyangc.bootsharding.entity.UserInfo;
import com.oyangc.bootsharding.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
public class UserController {
	@Autowired
	private IUserService userService;

	@PostMapping("/save")
	public AjaxResult insertData(@RequestBody UserInfo userInfo) {
		int i = userService.insertUser(userInfo);
		return AjaxResult.success(userInfo);
	}

	@GetMapping("/{id}")
	public AjaxResult getData(@PathVariable Long id) {
		UserInfo userInfoByUserId = userService.getUserInfoByUserId(id);
		return AjaxResult.success(userInfoByUserId);
	}
}
