/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.model;

/**
 *
 * @author Rangel
 */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "time_records")
public class TimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_type", nullable = false)
    private RecordType type;

    protected TimeRecord() {
        // Required by JPA
    }

    public TimeRecord(User user, LocalDateTime recordedAt, RecordType type) {
        this.user = user;
        this.recordedAt = recordedAt;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public RecordType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TimeRecord{id=" + id + ", recordedAt=" + recordedAt + ", type=" + type + "}";
    }
}
