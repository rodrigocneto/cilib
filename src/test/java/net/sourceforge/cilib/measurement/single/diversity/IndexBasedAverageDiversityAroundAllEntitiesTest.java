/**
 * Computational Intelligence Library (CIlib)
 * Copyright (C) 2003 - 2010
 * Computational Intelligence Research Group (CIRG@UP)
 * Department of Computer Science
 * University of Pretoria
 * South Africa
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, see <http://www.gnu.org/licenses/>.
 */
package net.sourceforge.cilib.measurement.single.diversity;

import net.sourceforge.cilib.controlparameter.ConstantControlParameter;
import net.sourceforge.cilib.entity.Topology;
import net.sourceforge.cilib.entity.topologies.GBestTopology;
import net.sourceforge.cilib.pso.particle.ParameterizedParticle;
import junit.framework.Assert;
import net.sourceforge.cilib.controlparameter.ParameterAdaptingControlParameter;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.functions.continuous.unconstrained.Spherical;
import net.sourceforge.cilib.measurement.generic.Iterations;
import net.sourceforge.cilib.problem.FunctionMinimisationProblem;
import net.sourceforge.cilib.pso.PSO;
import net.sourceforge.cilib.stoppingcondition.Maximum;
import net.sourceforge.cilib.stoppingcondition.MeasuredStoppingCondition;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kristina
 */
public class IndexBasedAverageDiversityAroundAllEntitiesTest {
    
    PSO pso;
    FunctionMinimisationProblem problem;
    ParameterAdaptingControlParameter parameter;
    Topology<ParameterizedParticle> topology;
    IndexBasedAverageDiversityAroundAllEntities diversityMeasure;
    
    public IndexBasedAverageDiversityAroundAllEntitiesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void initialise() {
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getValue method, of class IndexBasedAverageDiversityAroundAllEntities.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
         
        problem = new FunctionMinimisationProblem();
        problem.setDomain("R(-5.12:5.12)^5");
        problem.setFunction(new Spherical());
                
        pso = new PSO();
        
        pso.setOptimisationProblem(problem);
        pso.addStoppingCondition(new MeasuredStoppingCondition(new Iterations(), new Maximum(), 100));
        //pso.setInitialisationStrategy(newStrategy);
        pso.initialise();
        
        parameter = ConstantControlParameter.of(0);
        diversityMeasure = new IndexBasedAverageDiversityAroundAllEntities();
        
        testConvergedDiversity();
        
        testDiversityValues();
        
        
    }
   
    /*
     * This method tests two things:
     * 1- Does the class handle parameter indexes correctly?
     * 2- Is the diversity value 0 if all the particles are the same?
     */
    private void testConvergedDiversity() {
        ParameterizedParticle instance = new ParameterizedParticle();
        instance.setInertia(parameter);
        instance.setSocialAcceleration(parameter);
        instance.setCognitiveAcceleration(parameter);
        instance.setVmax(parameter);
        instance.initialise(problem);
        
        Vector candidateSolution = Vector.of(1,2,3,4,5);
        Vector velocity = Vector.of(0,0,0,0,0);
        instance.setCandidateSolution(candidateSolution);
        instance.getProperties().put(EntityType.Particle.VELOCITY, velocity);
        instance.getProperties().put(EntityType.Particle.BEST_POSITION, candidateSolution);
        
        ParameterizedParticle nBest = new ParameterizedParticle();
        nBest.setCandidateSolution(candidateSolution);
        nBest.getProperties().put(EntityType.Particle.BEST_POSITION, candidateSolution);
        nBest.getProperties().put(EntityType.Particle.BEST_FITNESS, instance.getBestFitness());
        nBest.getProperties().put(EntityType.FITNESS, instance.getFitness());
        instance.setNeighbourhoodBest(nBest);
        
        topology = new GBestTopology();
        topology.add(instance);
        topology.add(instance.getClone());
        pso.setTopology(topology);
        pso.run();
        
        diversityMeasure.setIndex(5); //inertia
        double value = diversityMeasure.getValue(pso).doubleValue();
        
        Assert.assertEquals(value, 0.0);
    }
    
    /*
     * This method tests whether the calculated value for the diversity of index 1 is correct
     */
    private void testDiversityValues() {
        ParameterizedParticle instance = new ParameterizedParticle();
        instance.setInertia(parameter);
        instance.setSocialAcceleration(parameter);
        instance.setCognitiveAcceleration(parameter);
        instance.setVmax(parameter);
        instance.initialise(problem);
        
        Vector candidateSolution = Vector.of(1,2,3,4,5);
        Vector velocity = Vector.of(0,0,0,0,0);
        instance.setCandidateSolution(candidateSolution);
        instance.getProperties().put(EntityType.Particle.VELOCITY, velocity);
        instance.getProperties().put(EntityType.Particle.BEST_POSITION, candidateSolution);
        
        ParameterizedParticle nBest = new ParameterizedParticle();
        nBest.setCandidateSolution(candidateSolution);
        nBest.getProperties().put(EntityType.Particle.BEST_POSITION, candidateSolution);
        nBest.getProperties().put(EntityType.Particle.BEST_FITNESS, instance.getBestFitness());
        nBest.getProperties().put(EntityType.FITNESS, instance.getFitness());
        instance.setNeighbourhoodBest(nBest);
        
        ParameterizedParticle instance2 = instance.getClone();
        
        Vector candidateSolution2 = Vector.of(5,7,3,2,1);
        instance2.setCandidateSolution(candidateSolution2);
        instance2.getProperties().put(EntityType.Particle.BEST_POSITION, candidateSolution2);
        instance2.setNeighbourhoodBest(nBest);
        
        ParameterizedParticle instance3 = instance.getClone();
        
        Vector candidateSolution3 = Vector.of(5,4,3,2,1);
        instance3.setCandidateSolution(candidateSolution3);
        instance3.getProperties().put(EntityType.Particle.BEST_POSITION, candidateSolution3);
        
        instance3.setNeighbourhoodBest(nBest);
        
        topology = new GBestTopology();
        topology.add(instance);
        topology.add(instance2);
        topology.add(instance3);
        pso.setTopology(topology);
        pso.run();
        
        diversityMeasure = new IndexBasedAverageDiversityAroundAllEntities();
        
        diversityMeasure.setIndex(1); //index 1 of particle
        double value = diversityMeasure.getValue(pso).doubleValue();
        Assert.assertEquals(value, -9.251858538542972E-17);
    }

    /**
     * Test of setIndex method, of class IndexBasedAverageDiversityAroundAllEntities.
     */
    @Test
    public void testSetIndex() {
        System.out.println("setIndex");
        IndexBasedAverageDiversityAroundAllEntities instance = new IndexBasedAverageDiversityAroundAllEntities();
        instance.setIndex(10);
        Assert.assertEquals(instance.getIndex(), 10);
    }
    
    /**
     * Test of getIndex method, of class IndexBasedAverageDiversityAroundAllEntities.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        IndexBasedAverageDiversityAroundAllEntities instance = new IndexBasedAverageDiversityAroundAllEntities();
        instance.setIndex(10);
        Assert.assertEquals(instance.getIndex(), 10);
    }
}