package nz.ac.uclive.jla201.studytracker.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import nz.ac.uclive.jla201.studytracker.Subject
import nz.ac.uclive.jla201.studytracker.repository.SubjectRepository

class SubjectViewModel(private val subjectRepository: SubjectRepository): ViewModel() {

    val subjects: LiveData<List<Subject>> = subjectRepository.subjects.asLiveData()
    val numSubjects: LiveData<Int> = subjectRepository.numSubjects.asLiveData()

    fun addSubject(subject: Subject) = viewModelScope.launch {
        subjectRepository.insert(subject)
    }

    fun getSubject(subjectId: Int): LiveData<Subject> {
        return subjectRepository.findById(subjectId).asLiveData()
    }
}