<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="jhdApp.tops.home.createOrEditLabel" v-text="$t('jhdApp.tops.home.createOrEditLabel')">Create or edit a Tops</h2>
                <div>
                    <div class="form-group" v-if="tops.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="tops.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('jhdApp.tops.title')" for="tops-title">Title</label>
                        <input type="text" class="form-control" name="title" id="tops-title"
                            :class="{'valid': !$v.tops.title.$invalid, 'invalid': $v.tops.title.$invalid }" v-model="$v.tops.title.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('jhdApp.tops.link')" for="tops-link">Link</label>
                        <input type="text" class="form-control" name="link" id="tops-link"
                            :class="{'valid': !$v.tops.link.$invalid, 'invalid': $v.tops.link.$invalid }" v-model="$v.tops.link.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('jhdApp.tops.source')" for="tops-source">Source</label>
                        <input type="text" class="form-control" name="source" id="tops-source"
                            :class="{'valid': !$v.tops.source.$invalid, 'invalid': $v.tops.source.$invalid }" v-model="$v.tops.source.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('jhdApp.tops.time')" for="tops-time">Time</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="tops-time"
                                    v-model="$v.tops.time.$model"
                                    name="time"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="tops-time" type="text" class="form-control" name="time"  :class="{'valid': !$v.tops.time.$invalid, 'invalid': $v.tops.time.$invalid }"
                            v-model="$v.tops.time.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('jhdApp.tops.seq')" for="tops-seq">Seq</label>
                        <input type="number" class="form-control" name="seq" id="tops-seq"
                            :class="{'valid': !$v.tops.seq.$invalid, 'invalid': $v.tops.seq.$invalid }" v-model.number="$v.tops.seq.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('jhdApp.tops.note')" for="tops-note">Note</label>
                        <input type="text" class="form-control" name="note" id="tops-note"
                            :class="{'valid': !$v.tops.note.$invalid, 'invalid': $v.tops.note.$invalid }" v-model="$v.tops.note.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.tops.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./tops-update.component.ts">
</script>
