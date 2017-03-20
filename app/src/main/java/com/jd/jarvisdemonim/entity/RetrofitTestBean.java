package com.jd.jarvisdemonim.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/17 0017
 * Name:
 * OverView:
 * Usage:
 */

public class RetrofitTestBean implements Serializable{

    private String FileServer;
    private int PageSize;
    private int Page;
    private int Rowcount;
    private int TotalPage;
    /**
     * Guid : 0E2BC7F2-1A35-4A42-9101-82E6DBB14891
     * SiteId : e2508231-5952-4c9a-b3ea-81517c691394
     * SiteName : 杭州图书馆图书库
     * PartId : 1f622eee-8220-4a62-881a-ed76fe540141
     * PartName : 法律
     * ParentId : null
     * Name : 图书馆法律问题研究
     * Format : null
     * AlbumId : null
     * Type : 1
     * IsAudit : 0
     * CreatedDate : /Date(-62135596800000+0800)/
     * C_Creator : null
     * Read : 2
     * Play : 0
     * Download : 0
     * Evaluate : 0
     * Score : 0
     * ScoreCount : 0
     * Forward : 0
     * Share : 0
     * Recommend : 0
     * Comment : 0
     * ShowOrder : 0
     * Album : {"Guid":"0E2BC7F2-1A35-4A42-9101-82E6DBB14891","ParentId":null,"ParentS_Id":null,"ResourceId":null,"PartGuid":null,"ShowOrder":0,"PartName":"法律","Url":"","Icon":"http://img1.doubanio.com/lpic/s6026634.jpg","Property":[{"Guid":null,"AlbumId":"0E2BC7F2-1A35-4A42-9101-82E6DBB14891","ElementPTagName":"Title","PropertyName":"书名","PropertyValue":"图书馆法律问题研究","PropertyMaxValue":"","PropertyClearValue":"图书馆法律问题研究","DictionaryId":null},{"Guid":null,"AlbumId":"0E2BC7F2-1A35-4A42-9101-82E6DBB14891","ElementPTagName":"Creator","PropertyName":"作者","PropertyValue":"王玉林著","PropertyMaxValue":"","PropertyClearValue":"王玉林著","DictionaryId":null},{"Guid":null,"AlbumId":"0E2BC7F2-1A35-4A42-9101-82E6DBB14891","ElementPTagName":"Description","PropertyName":"简介","PropertyValue":"　　《图书馆法律问题研究》一书是笔者在多年学术研究基础上完成的，是对相关研究的总结与提炼。书中运用法理学与部门法原理全面探讨了图书馆工作中存在的各种法律问题，既有理论性很强的内容，也有与实践结合紧密具有很强针对性和实用性的内容，对图书馆工作中遇到的各种法律问题的解决具有一定指导意义。","PropertyMaxValue":"","PropertyClearValue":"《图书馆法律问题研究》一书是笔者在多年学术研究基础上完成的，是对相关研究的总结与提炼。书中运用法理学与部门法原理全面探讨了图书馆工作中存在的各种法律问题，既有理论性很强的内容，也有与实践结合紧密具有很强针对性和实用性的内容，对图书馆工作中遇到的各种法律问题的解决具有一定指导意义。","DictionaryId":null},{"Guid":null,"AlbumId":"0E2BC7F2-1A35-4A42-9101-82E6DBB14891","ElementPTagName":"Contributor","PropertyName":"ISBN","PropertyValue":"978-7-81093-909-6","PropertyMaxValue":"","PropertyClearValue":"978-7-81093-909-6","DictionaryId":null},{"Guid":null,"AlbumId":"0E2BC7F2-1A35-4A42-9101-82E6DBB14891","ElementPTagName":"Publisher","PropertyName":"出版社","PropertyValue":"合肥工业大学出版社","PropertyMaxValue":"","PropertyClearValue":"合肥工业大学出版社","DictionaryId":null}],"File":null,"ChildAlbumInfo":null,"PartClass":null,"CreatedDate":"/Date(1481286785000+0800)/","ResourcesTypeCode":"Doc","FileCount":0,"Read":2,"Play":0,"Download":0,"Evaluate":0,"Score":0,"ScoreCount":0,"Forward":0,"Share":0,"Recommend":0,"Comment":0,"PageUrl":"http://tsk.hzzhwhy.cn/tsk/Show_C.html?guid=0E2BC7F2-1A35-4A42-9101-82E6DBB14891","ResourceFileCount":0,"Title":null,"Description":null,"C_Description":""}
     * Article : null
     * AlbumFile : null
     */

    private List<DataBean> Data;

    public String getFileServer() {
        return FileServer;
    }

    public void setFileServer(String FileServer) {
        this.FileServer = FileServer;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int PageSize) {
        this.PageSize = PageSize;
    }

    public int getPage() {
        return Page;
    }

    public void setPage(int Page) {
        this.Page = Page;
    }

    public int getRowcount() {
        return Rowcount;
    }

    public void setRowcount(int Rowcount) {
        this.Rowcount = Rowcount;
    }

    public int getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(int TotalPage) {
        this.TotalPage = TotalPage;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(DataBean bean:Data){
            stringBuilder.append(bean.toString());
        }
        return "RetrofitTestBean{" +
                "FileServer='" + FileServer + '\'' +
                ", PageSize=" + PageSize +
                ", Page=" + Page +
                ", Rowcount=" + Rowcount +
                ", TotalPage=" + TotalPage +
                ", Data=" + "\n"+stringBuilder.toString() +
                '}';
    }

    public static class DataBean implements Serializable{
        private String Guid;
        private String SiteId;
        private String SiteName;
        private String PartId;
        private String PartName;
        private Object ParentId;
        private String Name;
        private Object Format;
        private Object AlbumId;
        private int Type;
        private int IsAudit;
        private String CreatedDate;
        private Object C_Creator;
        private int Read;
        private int Play;
        private int Download;
        private int Evaluate;
        private int Score;
        private int ScoreCount;
        private int Forward;
        private int Share;
        private int Recommend;
        private int Comment;
        private int ShowOrder;
        /**
         * Guid : 0E2BC7F2-1A35-4A42-9101-82E6DBB14891
         * ParentId : null
         * ParentS_Id : null
         * ResourceId : null
         * PartGuid : null
         * ShowOrder : 0
         * PartName : 法律
         * Url :
         * Icon : http://img1.doubanio.com/lpic/s6026634.jpg
         * Property : [{"Guid":null,"AlbumId":"0E2BC7F2-1A35-4A42-9101-82E6DBB14891","ElementPTagName":"Title","PropertyName":"书名","PropertyValue":"图书馆法律问题研究","PropertyMaxValue":"","PropertyClearValue":"图书馆法律问题研究","DictionaryId":null},{"Guid":null,"AlbumId":"0E2BC7F2-1A35-4A42-9101-82E6DBB14891","ElementPTagName":"Creator","PropertyName":"作者","PropertyValue":"王玉林著","PropertyMaxValue":"","PropertyClearValue":"王玉林著","DictionaryId":null},{"Guid":null,"AlbumId":"0E2BC7F2-1A35-4A42-9101-82E6DBB14891","ElementPTagName":"Description","PropertyName":"简介","PropertyValue":"　　《图书馆法律问题研究》一书是笔者在多年学术研究基础上完成的，是对相关研究的总结与提炼。书中运用法理学与部门法原理全面探讨了图书馆工作中存在的各种法律问题，既有理论性很强的内容，也有与实践结合紧密具有很强针对性和实用性的内容，对图书馆工作中遇到的各种法律问题的解决具有一定指导意义。","PropertyMaxValue":"","PropertyClearValue":"《图书馆法律问题研究》一书是笔者在多年学术研究基础上完成的，是对相关研究的总结与提炼。书中运用法理学与部门法原理全面探讨了图书馆工作中存在的各种法律问题，既有理论性很强的内容，也有与实践结合紧密具有很强针对性和实用性的内容，对图书馆工作中遇到的各种法律问题的解决具有一定指导意义。","DictionaryId":null},{"Guid":null,"AlbumId":"0E2BC7F2-1A35-4A42-9101-82E6DBB14891","ElementPTagName":"Contributor","PropertyName":"ISBN","PropertyValue":"978-7-81093-909-6","PropertyMaxValue":"","PropertyClearValue":"978-7-81093-909-6","DictionaryId":null},{"Guid":null,"AlbumId":"0E2BC7F2-1A35-4A42-9101-82E6DBB14891","ElementPTagName":"Publisher","PropertyName":"出版社","PropertyValue":"合肥工业大学出版社","PropertyMaxValue":"","PropertyClearValue":"合肥工业大学出版社","DictionaryId":null}]
         * File : null
         * ChildAlbumInfo : null
         * PartClass : null
         * CreatedDate : /Date(1481286785000+0800)/
         * ResourcesTypeCode : Doc
         * FileCount : 0
         * Read : 2
         * Play : 0
         * Download : 0
         * Evaluate : 0
         * Score : 0
         * ScoreCount : 0
         * Forward : 0
         * Share : 0
         * Recommend : 0
         * Comment : 0
         * PageUrl : http://tsk.hzzhwhy.cn/tsk/Show_C.html?guid=0E2BC7F2-1A35-4A42-9101-82E6DBB14891
         * ResourceFileCount : 0
         * Title : null
         * Description : null
         * C_Description :
         */

        private AlbumBean Album;
        private Object Article;
        private Object AlbumFile;

        public String getGuid() {
            return Guid;
        }

        public void setGuid(String Guid) {
            this.Guid = Guid;
        }

        public String getSiteId() {
            return SiteId;
        }

        public void setSiteId(String SiteId) {
            this.SiteId = SiteId;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "Guid='" + Guid + '\'' +
                    ", SiteId='" + SiteId + '\'' +
                    ", SiteName='" + SiteName + '\'' +
                    ", PartId='" + PartId + '\'' +
                    ", PartName='" + PartName + '\'' +
                    ", ParentId=" + ParentId +
                    ", Name='" + Name + '\'' +
                    ", Format=" + Format +
                    ", AlbumId=" + AlbumId +
                    ", Type=" + Type +
                    ", IsAudit=" + IsAudit +
                    ", CreatedDate='" + CreatedDate + '\'' +
                    ", C_Creator=" + C_Creator +
                    ", Read=" + Read +
                    ", Play=" + Play +
                    ", Download=" + Download +
                    ", Evaluate=" + Evaluate +
                    ", Score=" + Score +
                    ", ScoreCount=" + ScoreCount +
                    ", Forward=" + Forward +
                    ", Share=" + Share +
                    ", Recommend=" + Recommend +
                    ", Comment=" + Comment +
                    ", ShowOrder=" + ShowOrder +
                    ", Album=" + Album +
                    ", Article=" + Article +
                    ", AlbumFile=" + AlbumFile +
                    '}';
        }

        public String getSiteName() {
            return SiteName;
        }

        public void setSiteName(String SiteName) {
            this.SiteName = SiteName;
        }

        public String getPartId() {
            return PartId;
        }

        public void setPartId(String PartId) {
            this.PartId = PartId;
        }

        public String getPartName() {
            return PartName;
        }

        public void setPartName(String PartName) {
            this.PartName = PartName;
        }

        public Object getParentId() {
            return ParentId;
        }

        public void setParentId(Object ParentId) {
            this.ParentId = ParentId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public Object getFormat() {
            return Format;
        }

        public void setFormat(Object Format) {
            this.Format = Format;
        }

        public Object getAlbumId() {
            return AlbumId;
        }

        public void setAlbumId(Object AlbumId) {
            this.AlbumId = AlbumId;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public int getIsAudit() {
            return IsAudit;
        }

        public void setIsAudit(int IsAudit) {
            this.IsAudit = IsAudit;
        }

        public String getCreatedDate() {
            return CreatedDate;
        }

        public void setCreatedDate(String CreatedDate) {
            this.CreatedDate = CreatedDate;
        }

        public Object getC_Creator() {
            return C_Creator;
        }

        public void setC_Creator(Object C_Creator) {
            this.C_Creator = C_Creator;
        }

        public int getRead() {
            return Read;
        }

        public void setRead(int Read) {
            this.Read = Read;
        }

        public int getPlay() {
            return Play;
        }

        public void setPlay(int Play) {
            this.Play = Play;
        }

        public int getDownload() {
            return Download;
        }

        public void setDownload(int Download) {
            this.Download = Download;
        }

        public int getEvaluate() {
            return Evaluate;
        }

        public void setEvaluate(int Evaluate) {
            this.Evaluate = Evaluate;
        }

        public int getScore() {
            return Score;
        }

        public void setScore(int Score) {
            this.Score = Score;
        }

        public int getScoreCount() {
            return ScoreCount;
        }

        public void setScoreCount(int ScoreCount) {
            this.ScoreCount = ScoreCount;
        }

        public int getForward() {
            return Forward;
        }

        public void setForward(int Forward) {
            this.Forward = Forward;
        }

        public int getShare() {
            return Share;
        }

        public void setShare(int Share) {
            this.Share = Share;
        }

        public int getRecommend() {
            return Recommend;
        }

        public void setRecommend(int Recommend) {
            this.Recommend = Recommend;
        }

        public int getComment() {
            return Comment;
        }

        public void setComment(int Comment) {
            this.Comment = Comment;
        }

        public int getShowOrder() {
            return ShowOrder;
        }

        public void setShowOrder(int ShowOrder) {
            this.ShowOrder = ShowOrder;
        }

        public AlbumBean getAlbum() {
            return Album;
        }

        public void setAlbum(AlbumBean Album) {
            this.Album = Album;
        }

        public Object getArticle() {
            return Article;
        }

        public void setArticle(Object Article) {
            this.Article = Article;
        }

        public Object getAlbumFile() {
            return AlbumFile;
        }

        public void setAlbumFile(Object AlbumFile) {
            this.AlbumFile = AlbumFile;
        }

        public static class AlbumBean {
            private String Guid;
            private Object ParentId;
            private Object ParentS_Id;
            private Object ResourceId;
            private Object PartGuid;
            private int ShowOrder;
            private String PartName;
            private String Url;
            private String Icon;
            private Object File;
            private Object ChildAlbumInfo;
            private Object PartClass;
            private String CreatedDate;
            private String ResourcesTypeCode;
            private int FileCount;
            private int Read;
            private int Play;
            private int Download;
            private int Evaluate;
            private int Score;
            private int ScoreCount;
            private int Forward;
            private int Share;
            private int Recommend;
            private int Comment;
            private String PageUrl;
            private int ResourceFileCount;
            private Object Title;
            private Object Description;
            private String C_Description;
            /**
             * Guid : null
             * AlbumId : 0E2BC7F2-1A35-4A42-9101-82E6DBB14891
             * ElementPTagName : Title
             * PropertyName : 书名
             * PropertyValue : 图书馆法律问题研究
             * PropertyMaxValue :
             * PropertyClearValue : 图书馆法律问题研究
             * DictionaryId : null
             */

            private List<PropertyBean> Property;

            public String getGuid() {
                return Guid;
            }

            public void setGuid(String Guid) {
                this.Guid = Guid;
            }

            public Object getParentId() {
                return ParentId;
            }

            public void setParentId(Object ParentId) {
                this.ParentId = ParentId;
            }

            public Object getParentS_Id() {
                return ParentS_Id;
            }

            public void setParentS_Id(Object ParentS_Id) {
                this.ParentS_Id = ParentS_Id;
            }

            public Object getResourceId() {
                return ResourceId;
            }

            public void setResourceId(Object ResourceId) {
                this.ResourceId = ResourceId;
            }

            public Object getPartGuid() {
                return PartGuid;
            }

            public void setPartGuid(Object PartGuid) {
                this.PartGuid = PartGuid;
            }

            public int getShowOrder() {
                return ShowOrder;
            }

            public void setShowOrder(int ShowOrder) {
                this.ShowOrder = ShowOrder;
            }

            public String getPartName() {
                return PartName;
            }

            public void setPartName(String PartName) {
                this.PartName = PartName;
            }

            public String getUrl() {
                return Url;
            }

            public void setUrl(String Url) {
                this.Url = Url;
            }

            public String getIcon() {
                return Icon;
            }

            public void setIcon(String Icon) {
                this.Icon = Icon;
            }

            public Object getFile() {
                return File;
            }

            public void setFile(Object File) {
                this.File = File;
            }

            public Object getChildAlbumInfo() {
                return ChildAlbumInfo;
            }

            public void setChildAlbumInfo(Object ChildAlbumInfo) {
                this.ChildAlbumInfo = ChildAlbumInfo;
            }

            public Object getPartClass() {
                return PartClass;
            }

            public void setPartClass(Object PartClass) {
                this.PartClass = PartClass;
            }

            public String getCreatedDate() {
                return CreatedDate;
            }

            public void setCreatedDate(String CreatedDate) {
                this.CreatedDate = CreatedDate;
            }

            public String getResourcesTypeCode() {
                return ResourcesTypeCode;
            }

            public void setResourcesTypeCode(String ResourcesTypeCode) {
                this.ResourcesTypeCode = ResourcesTypeCode;
            }

            public int getFileCount() {
                return FileCount;
            }

            public void setFileCount(int FileCount) {
                this.FileCount = FileCount;
            }

            public int getRead() {
                return Read;
            }

            public void setRead(int Read) {
                this.Read = Read;
            }

            public int getPlay() {
                return Play;
            }

            public void setPlay(int Play) {
                this.Play = Play;
            }

            public int getDownload() {
                return Download;
            }

            public void setDownload(int Download) {
                this.Download = Download;
            }

            public int getEvaluate() {
                return Evaluate;
            }

            public void setEvaluate(int Evaluate) {
                this.Evaluate = Evaluate;
            }

            public int getScore() {
                return Score;
            }

            public void setScore(int Score) {
                this.Score = Score;
            }

            public int getScoreCount() {
                return ScoreCount;
            }

            public void setScoreCount(int ScoreCount) {
                this.ScoreCount = ScoreCount;
            }

            public int getForward() {
                return Forward;
            }

            public void setForward(int Forward) {
                this.Forward = Forward;
            }

            public int getShare() {
                return Share;
            }

            public void setShare(int Share) {
                this.Share = Share;
            }

            public int getRecommend() {
                return Recommend;
            }

            public void setRecommend(int Recommend) {
                this.Recommend = Recommend;
            }

            public int getComment() {
                return Comment;
            }

            public void setComment(int Comment) {
                this.Comment = Comment;
            }

            public String getPageUrl() {
                return PageUrl;
            }

            public void setPageUrl(String PageUrl) {
                this.PageUrl = PageUrl;
            }

            public int getResourceFileCount() {
                return ResourceFileCount;
            }

            public void setResourceFileCount(int ResourceFileCount) {
                this.ResourceFileCount = ResourceFileCount;
            }

            public Object getTitle() {
                return Title;
            }

            public void setTitle(Object Title) {
                this.Title = Title;
            }

            public Object getDescription() {
                return Description;
            }

            public void setDescription(Object Description) {
                this.Description = Description;
            }

            public String getC_Description() {
                return C_Description;
            }

            public void setC_Description(String C_Description) {
                this.C_Description = C_Description;
            }

            public List<PropertyBean> getProperty() {
                return Property;
            }

            public void setProperty(List<PropertyBean> Property) {
                this.Property = Property;
            }

            public static class PropertyBean {
                private Object Guid;
                private String AlbumId;
                private String ElementPTagName;
                private String PropertyName;
                private String PropertyValue;
                private String PropertyMaxValue;
                private String PropertyClearValue;
                private Object DictionaryId;

                public Object getGuid() {
                    return Guid;
                }

                public void setGuid(Object Guid) {
                    this.Guid = Guid;
                }

                public String getAlbumId() {
                    return AlbumId;
                }

                public void setAlbumId(String AlbumId) {
                    this.AlbumId = AlbumId;
                }

                public String getElementPTagName() {
                    return ElementPTagName;
                }

                public void setElementPTagName(String ElementPTagName) {
                    this.ElementPTagName = ElementPTagName;
                }

                public String getPropertyName() {
                    return PropertyName;
                }

                public void setPropertyName(String PropertyName) {
                    this.PropertyName = PropertyName;
                }

                public String getPropertyValue() {
                    return PropertyValue;
                }

                public void setPropertyValue(String PropertyValue) {
                    this.PropertyValue = PropertyValue;
                }

                public String getPropertyMaxValue() {
                    return PropertyMaxValue;
                }

                public void setPropertyMaxValue(String PropertyMaxValue) {
                    this.PropertyMaxValue = PropertyMaxValue;
                }

                public String getPropertyClearValue() {
                    return PropertyClearValue;
                }

                public void setPropertyClearValue(String PropertyClearValue) {
                    this.PropertyClearValue = PropertyClearValue;
                }

                public Object getDictionaryId() {
                    return DictionaryId;
                }

                public void setDictionaryId(Object DictionaryId) {
                    this.DictionaryId = DictionaryId;
                }
            }
        }
    }
}
