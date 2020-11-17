<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="jhdApp.news.home.createOrEditLabel" v-text="$t('jhdApp.news.home.createOrEditLabel')">Create or edit a News</h2>
                <div>
                    <div class="form-group" v-if="news.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="news.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('jhdApp.news.title')" for="news-title">Title</label>
                        <input type="text" class="form-control" name="title" id="news-title"
                            :class="{'valid': !$v.news.title.$invalid, 'invalid': $v.news.title.$invalid }" v-model="$v.news.title.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('jhdApp.news.source')" for="news-source">Source</label>
                        <input type="text" class="form-control" name="source" id="news-source"
                            :class="{'valid': !$v.news.source.$invalid, 'invalid': $v.news.source.$invalid }" v-model="$v.news.source.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('jhdApp.news.link')" for="news-link">Link</label>
                        <input type="text" class="form-control" name="link" id="news-link"
                            :class="{'valid': !$v.news.link.$invalid, 'invalid': $v.news.link.$invalid }" v-model="$v.news.link.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('jhdApp.news.kind')" for="news-kind">Kind</label>
                        <input type="text" class="form-control" name="kind" id="news-kind"
                            :class="{'valid': !$v.news.kind.$invalid, 'invalid': $v.news.kind.$invalid }" v-model="$v.news.kind.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('jhdApp.news.time')" for="news-time">Time</label>
                        <div class="d-flex">
                            <input id="news-time" type="datetime-local" class="form-control" name="time" :class="{'valid': !$v.news.time.$invalid, 'invalid': $v.news.time.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.news.time.$model)"
                            @change="updateInstantField('time', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('jhdApp.news.content')" for="news-content">Content</label>
                        <input type="text" class="form-control" name="content" id="news-content"
                            :class="{'valid': !$v.news.content.$invalid, 'invalid': $v.news.content.$invalid }" v-model="$v.news.content.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.news.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./news-update.component.ts">
</script>
