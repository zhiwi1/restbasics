package com.epam.esm.controller.entity;

import java.util.Objects;

public class CertificateTag {
    private int certificateId;
    private int tagId;

    public CertificateTag() {

    }

    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int certificateId) {
        this.certificateId = certificateId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateTag that = (CertificateTag) o;
        return certificateId == that.certificateId && tagId == that.tagId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificateId, tagId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CertificateTag{");
        sb.append("certificateId=").append(certificateId);
        sb.append(", tagId=").append(tagId);
        sb.append('}');
        return sb.toString();
    }
}
