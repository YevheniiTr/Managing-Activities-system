package com.yevhenii.ticketingService.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name= "files")
public class ProveFile{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(name = "f_name")
    String name;

    @Column(name = "mime_type")
    String mimeType;
    @Column(name = "file_data")
    @Lob
    private byte[] data;
    @Column(name = "f_size")
    private long fileSize;


    @OneToOne(mappedBy =  "proveFile",fetch =  FetchType.LAZY)
    Application application;

    public ProveFile(String id) {
        this.id = id;
        fileSize = 0;
        data = null;
        mimeType = null;
        name = null;
    }


//    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    User user;




}
