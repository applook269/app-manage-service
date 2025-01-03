package com.hooke.zdl.admin.module.business.controller;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.Team;
import com.hooke.zdl.admin.module.business.entity.TeamMember;
import com.hooke.zdl.admin.module.business.service.TeamAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/team")
@Tag(name = "团队", description = "团队")
public class TeamAdminController {
    @Autowired
    private TeamAdminService teamAdminService;

    @Operation(summary = "团队分页")
    @GetMapping("/page")
    public ResponseDTO<PageResult<Team>> pageTeam(Team team, PageParam pageParam) {
        PageResult<Team> model = teamAdminService.pageTeam(team, pageParam);
        return ResponseDTO.ok(model);
    }

    @Operation(summary = "团队成员列表")
    @GetMapping("/list-member")
    public ResponseDTO<List<TeamMember>> listTeamMember(TeamMember teamMember) {
        List<TeamMember> members = teamAdminService.listTeamMember(teamMember);
        return ResponseDTO.ok(members);
    }
}
