package com.epam.esm.service;

public interface TagService {
    Set<Tag> getAllTags(String sortParameter, String orderParameter);

    Tag getTagById(int id) throws ResourceNotFoundServiceException;

    void deleteTagById(int id) throws InvalidRequestDataServiceException;

    Tag insertTag(Tag tag) throws ResourceConflictServiceException;
}
