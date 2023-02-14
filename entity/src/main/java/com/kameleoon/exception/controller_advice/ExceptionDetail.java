package com.kameleoon.exception.controller_advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDetail {
    private LocalDateTime date;
    private String title;
    private int status;
    private String detail;
    private String path;
    private String developerMessage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        ExceptionDetail ex = (ExceptionDetail) o;

        return Objects.equals(this.date, ex.date)
                && Objects.equals(this.title, ex.title)
                && Objects.equals(this.status, ex.status)
                && Objects.equals(this.detail, ex.detail)
                && Objects.equals(this.path, ex.path)
                && Objects.equals(this.developerMessage, ex.developerMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, title, status, detail, path, developerMessage);
    }
}
