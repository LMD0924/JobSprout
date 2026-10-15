package com.jobsprout.resume.controller;

import com.jobsprout.common.exception.BusinessException;
import com.jobsprout.common.result.Result;
import com.jobsprout.common.result.ResultCode;
import com.jobsprout.resume.dto.ResumeDTO;
import com.jobsprout.resume.service.ResumeService;
import com.jobsprout.resume.vo.ResumeVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/** 学生简历接口，身份由 Gateway 注入的 X-User-Id 请求头提供。 */
@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping("/my")
    public Result<ResumeVO> my(@RequestHeader(value = "X-User-Id", required = false) String userId) {
        return Result.success(resumeService.getMyResume(requireUserId(userId)));
    }

    @GetMapping("/{id}")
    public Result<ResumeVO> get(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                @PathVariable Long id) {
        return Result.success(resumeService.getById(requireUserId(userId), id));
    }

    @PostMapping
    public Result<ResumeVO> create(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                   @Valid @RequestBody ResumeDTO request) {
        return Result.success(resumeService.create(requireUserId(userId), request));
    }

    @PutMapping("/{id}")
    public Result<ResumeVO> update(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                   @PathVariable Long id, @Valid @RequestBody ResumeDTO request) {
        return Result.success(resumeService.update(requireUserId(userId), id, request));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestHeader(value = "X-User-Id", required = false) String userId,
                               @PathVariable Long id) {
        resumeService.delete(requireUserId(userId), id);
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
}
