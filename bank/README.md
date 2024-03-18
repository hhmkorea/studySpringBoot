# Junit Bank App
####
### Jpa LocalDateTime 자동으로 생성하는 법 
- @EnableJpaAuditing (Main 클래스)
  - @EntityListeners(AuditingEntityListener.class) (Entity 클래스)
    @CreatedDate // Insert할때 날짜 자동으로 들어감
  
        @Column(nullable = false)
        private LocalDateTime createdAt;
    
        @LastModifiedDate // Insert, Update할때 날짜 자동으로 들어감
        @Column(nullable = false)
        private LocalDateTime updatedAt;

