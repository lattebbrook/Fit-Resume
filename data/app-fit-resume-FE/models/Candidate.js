const mongoose = require('mongoose');

const candidateSchema = mongoose.Schema(
    { 
        fileName: {
            type: String,
            require: true,
            default: "ResumeApp"
        },
        name: {
            type: String,
            require: [true, "Please Enter the name"]
        },
        age: {
            type: String,
            require: [true, "Please Enter the age"]
        },
        dateOfBirth: {
            type: String,
            require: [true, "Please Enter the date of birth"]
        },
        tel: {
            type: String,
            require: [true, "Please Enter the telephone number"]
        },
        address: {
            type: String,
            require: [true, "Please Enter the address"]
        },
        degree: {
            type: String,
            require: [true, "Please Enter the degree"]
        },
        currentPosition: {
            type: String,
            require: [true, "Please Enter the current position"],
            default: "-"
        },
        currentWorkplace: {
            type: String,
            require: [true, "Please Enter the current workplace/previous workplace"],
            default: "-"
        },    
        durationOfWork: {
            type: String,
            require: [true, "Please Enter the working experiences"],
            default: "-"
        },
        skills: {
            type: String,
            require: [true, "Please Enter the working skills"]
        },
        expectedSalary: {
            type: String,
            require: [true, "Please Enter expected salary"],
            default: "-"
        },
        currentSalary: {
            type: String,
            require: [true, "Please Enter expected salary"]
        }
    },
    {
        timestamps: true
    }
);

candidateSchema.set('collection', 'candidate');
const candidate = mongoose.model('candidate', candidateSchema);

module.exports = candidate;