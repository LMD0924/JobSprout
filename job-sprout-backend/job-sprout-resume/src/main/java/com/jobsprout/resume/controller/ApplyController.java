package com.jobsprout.resume.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jobsprout.common.exception.BusinessException;
import com.jobsprout.common.result.Result;
import com.jobsprout.common.result.ResultCode;
import com.jobsprout.resume.dto.ApplyDTO;
import com.jobsprout.resume.dto.ApplyStatusDTO;
import com.jobsprout.resume.service.ApplyService;
import com.jobsprout.resume.vo.ApplyRecordVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/** 职位投递接口，学生和企业通过角色头访问各自操作。 */
@RestController
@RequestMapping("/api/apply")
@RequiredArgsConstructor
public class ApplyController {
    private final ApplyService applyService;

    @PostMapping
    public Result<ApplyRecordVO> apply(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                      @RequestHeader(value = "X-Role", required = false) String role,
                                      @Valid @RequestBody ApplyDTO request) {
        requireRole(role, "STUDENT");
        return Result.success(applyService.apply(requireUserId(userId), request));
    }

    @GetMapping("/my")
    public Result<Page<ApplyRecordVO>> my(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                          @RequestParam(defaultValue = "1") long pageNum,
                                          @RequestParam(defaultValue = "10") long pageSize) {
        return Result.success(applyService.getMyApplies(requireUserId(userId), pageNum, pageSize));
    }

    @GetMapping("/job/{jobId}")
    public Result<Page<ApplyRecordVO>> job(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                           @RequestHeader(value = "X-Role", required = false) String role,
                                           @PathVariable Long jobId,
                                           @RequestParam(defaultValue = "1") long pageNum,
                                           @RequestParam(defaultValue = "10") long pageSize) {
        requireRole(role, "COMPANY");
        return Result.success(applyService.getJobApplies(requireUserId(userId), jobId, pageNum, pageSize));
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                     @RequestHeader(value = "X-Role", required = false) String role,
                                     @PathVariable Long id, @Valid @RequestBody ApplyStatusDTO request) {
        requireRole(role, "COMPANY");
        applyService.updateStatus(requireUserId(userId), id, request);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> withdraw(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                 @RequestHeader(value = "X-Role", required = false) String role,
                                 @PathVariable Long id) {
        requireRole(role, "STUDENT");
        applyService.withdraw(requireUserId(userId), id);
        return Result.success();
    }

    private Long requireUserId(String value) {
        try {
            if (value == null || value.isBlank()) throw new NumberFormatException();
            return Long.valueOf(value);
        } catch (NumberFormatException ex) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "请先登录");
        }
    }

    private void requireRole(String actual, String expected) {
        if (!expected.equalsIgnoreCase(actual)) throw new BusinessException(ResultCode.FORBIDDEN, "当前身份无权执行此操作");
    }
}
